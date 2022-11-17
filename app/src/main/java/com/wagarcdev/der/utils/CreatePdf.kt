package com.wagarcdev.der.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.PageInfo
import android.text.StaticLayout
import android.text.TextPaint
import com.wagarcdev.der.domain.model.PdfContent
import com.wagarcdev.der.domain.model.TextConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import kotlin.math.roundToInt

/**
 * Interface to create a PDF file.
 */
interface CreatePdf {
    /**
     * Creates a FPD file.
     *
     * @param pdfContent the [PdfContent] to create the PDF.
     *
     * @return the PDF [File] or null if something went wrong.
     */
    suspend operator fun invoke(pdfContent: PdfContent): File?
}

/**
 * Implementation of [CreatePdf].
 */
class CreatePdfImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val coroutinesDispatchers: CoroutinesDispatchers
) : CreatePdf {
    // TODO(decide the pdf path)
    private val pdfsPath = "${context.filesDir}/reports"

    private val textPaint = TextPaint()

    private val pageWidth = 595
    private val pageHeight = 842
    private val margin = 40F

    private val canvasDensity = 72
    private val bitmapDensity = 320

    /**
     * Configures the [TextPaint] and creates a [StaticLayout].
     *
     * @param textToDrawn string value to drawn on Static Layout.
     * @param textConfig styles to apply on Text Paint.
     * @param horizontalMargin int value that represents the space o X axis. This value will
     * be applied in both sides of the Static Layout.
     *
     * @return [StaticLayout].
     */
    private fun TextPaint.createStaticLayout(
        textToDrawn: String,
        textConfig: TextConfig,
        horizontalMargin: Int
    ): StaticLayout {
        typeface = textConfig.textTypeface
        textSize = textConfig.textSize
        color = textConfig.textColor

        val startIndex = 0
        val endIndex = textToDrawn.length
        val width = pageWidth - (horizontalMargin * 2)

        return StaticLayout.Builder
            .obtain(textToDrawn, startIndex, endIndex, this, width)
            .setAlignment(textConfig.textAlign)
            .setLineSpacing(0F, 1F)
            .setIncludePad(false)
            .build()
    }

    /**
     * Drawn a [StaticLayout] on this [Canvas].
     *
     * @param startDistanceX float value to be used on X axis.
     * @param startDistanceY float value to be used on Y axis.
     * @param bottomSpace float value that apply a extra margin on bottom.
     * @param staticLayout the Static Layout that will be drawn on this Canvas.
     *
     * @return float value corresponding to height size consumed after draw. This value
     * should be used on [startDistanceY] on next drawn.
     */
    private fun Canvas.drawn(
        startDistanceX: Float,
        startDistanceY: Float,
        bottomSpace: Float,
        staticLayout: StaticLayout,
    ): Float {
        save()
        translate(startDistanceX, startDistanceY)
        staticLayout.draw(this)
        restore()

        return startDistanceY + bottomSpace + staticLayout.height
    }

    /**
     * Calculates a size for the bitmap based bitmap density and canvas density.
     *
     * @param size int value that represents the bitmap width or the bitmap height.
     *
     * @return the new [size] of this bitmap.
     */
    private fun bitmapSizeToCanvasSize(size: Int): Int {
        val density = bitmapDensity / canvasDensity
        return size / density
    }

    /**
     * Calculates if there is space available on the canvas to draw a bitmap on
     * the side after a X distance.
     *
     * IMPORTANT: [bitmapWidth] and [canvasWidth] with different densities
     * will produce invalid results.
     *
     * @param startDistance float value that represents the start distance on X axis.
     * @param bitmapWidth int value that represents the width size that the bitmap will occupy.
     * @param canvasWidth int value that represents the size of the canvas width.
     *
     * @return true if has space to drawn the bitmap on side or false if there is no space
     * and should drawn the bimap below.
     */
    private fun hasSpaceToDrawnBitmapOnSide(
        startDistance: Float,
        bitmapWidth: Int,
        canvasWidth: Int
    ): Boolean {
        val bitmapWidthWithDistanceAndMargin = bitmapWidth + startDistance + margin
        return canvasWidth > bitmapWidthWithDistanceAndMargin
    }

    /**
     * Calculates if there is space available on the canvas to draw a content
     * below after a Y distance.
     *
     * IMPORTANT: [contentHeight] and [canvasHeight] with different densities
     * will produce invalid results.
     *
     * @param startDistance float value that represents the start distance on Y axis.
     * @param contentHeight int value that represents the height size that the content will occupy.
     * @param canvasHeight int value that represents the size of the canvas height.
     *
     * @return true if has space to drawn the content below or false if there is no space
     * and should create a new page.
     */
    private fun hasSpaceToDrawnContentBelow(
        startDistance: Float,
        contentHeight: Int,
        canvasHeight: Int
    ): Boolean {
        val contentHeightWithDistanceAndMargin = contentHeight + startDistance + margin
        return canvasHeight > contentHeightWithDistanceAndMargin
    }

    /* the warning in FileOutputStream is a "false-positive" because this method runs on
    IO Dispatcher, apparently the IDE can't recognize a custom injected instance,
    so it's ok to use suppress BlockingMethodInNonBlockingContext here */
    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun invoke(
        pdfContent: PdfContent
    ): File? = withContext(context = coroutinesDispatchers.io) {
        return@withContext try {
            val pdfDocument = PdfDocument()

            val pdfsFilePath = File(pdfsPath)
            if (!pdfsFilePath.exists()) pdfsFilePath.mkdirs()

            val pdfFile = File(pdfsFilePath, "${pdfContent.fileName}.pdf")
            val pdfOutputStream = FileOutputStream(pdfFile)

            var distanceX = margin
            var distanceY = margin

            var pageNumber = 1
            var pageInfo = PageInfo.Builder(pageWidth, pageHeight, pageNumber).create()
            var page = pdfDocument.startPage(pageInfo)
            var canvas = page.canvas.apply { density = canvasDensity }

            pdfContent.displayTexts.forEach { displayText ->
                if (displayText.text.isNotBlank()) {
                    val staticLayout = textPaint.createStaticLayout(
                        textToDrawn = displayText.text,
                        textConfig = displayText.textConfig,
                        horizontalMargin = distanceX.roundToInt()
                    )

                    val hasSpaceToDrawnContentBelow = hasSpaceToDrawnContentBelow(
                        startDistance = distanceY,
                        contentHeight = staticLayout.height,
                        canvasHeight = canvas.height
                    )
                    if (!hasSpaceToDrawnContentBelow) {
                        pdfDocument.finishPage(page)
                        pageNumber++
                        pageInfo = PageInfo.Builder(pageWidth, pageHeight, pageNumber).create()
                        page = pdfDocument.startPage(pageInfo)
                        canvas = page.canvas.apply { density = canvasDensity }
                        distanceY = margin
                    }

                    canvas.drawn(
                        startDistanceX = distanceX,
                        startDistanceY = distanceY,
                        bottomSpace = displayText.textConfig.bottomSpace,
                        staticLayout = staticLayout
                    ).also { newStartDistanceY ->
                        distanceY = newStartDistanceY
                    }
                }
            }

            distanceY += margin

            var biggerHeightSize = 0

            pdfContent.bitmaps.forEach { imageBitmap ->
                val bitmap = imageBitmap.apply { density = bitmapDensity }

                val bitmapHeight = bitmapSizeToCanvasSize(size = bitmap.height)
                val bitmapWidth = bitmapSizeToCanvasSize(size = bitmap.width)

                biggerHeightSize = bitmapHeight.takeIf {
                    it > biggerHeightSize
                } ?: biggerHeightSize

                val hasSpaceToDrawnBitmapOnSide = hasSpaceToDrawnBitmapOnSide(
                    startDistance = distanceX,
                    bitmapWidth = bitmapWidth,
                    canvasWidth = canvas.width
                )
                if (!hasSpaceToDrawnBitmapOnSide) {
                    distanceX = margin
                    distanceY += biggerHeightSize
                    biggerHeightSize = 0
                }

                val hasSpaceToDrawnContentBelow = hasSpaceToDrawnContentBelow(
                    startDistance = distanceY,
                    contentHeight = bitmapHeight,
                    canvasHeight = canvas.height
                )
                if (!hasSpaceToDrawnContentBelow) {
                    pdfDocument.finishPage(page)
                    pageNumber++
                    pageInfo = PageInfo.Builder(pageWidth, pageHeight, pageNumber).create()
                    page = pdfDocument.startPage(pageInfo)
                    canvas = page.canvas.apply { density = canvasDensity }
                    distanceX = margin
                    distanceY = margin
                }

                canvas.drawBitmap(bitmap, distanceX, distanceY, null)

                distanceX += bitmapWidth
            }

            pdfDocument.apply {
                finishPage(page)
                writeTo(pdfOutputStream)
                close()
            }

            pdfOutputStream.apply {
                flush()
                close()
            }

            pdfFile
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}