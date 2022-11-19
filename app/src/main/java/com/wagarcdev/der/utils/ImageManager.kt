package com.wagarcdev.der.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

/**
 * Interface to manage image file.
 */
interface ImageManager {
    /**
     * Creates a image from a URI and save it on app directory.
     *
     * @param fileName string value for the name of this image. If empty or blank
     * the original file name will be used. Default is empty.
     * @param uri the [Uri] of the selected image.
     *
     * @return the new [Bitmap] of this [image file][File] or null if failed to create a File.
     */
    suspend fun saveImage(fileName: String = "", uri: Uri): Bitmap?

    /**
     * Creates a image from a URI and save it on app directory.
     *
     * @param fileName string value of the [image file][File].
     *
     * @return the [Bitmap] of this [image file][File] or null if the file don't exists.
     */
    suspend fun getImageBitmap(fileName: String): Bitmap?
}

/**
 * Implementation of [ImageManager].
 */
class ImageManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val coroutinesDispatchers: CoroutinesDispatchers
) : ImageManager {
    // TODO(decide the image path)
    private val imagesPath = "${context.filesDir}/images"

    /**
     * Formats a string, removing the char sequence before the last occurrence of "/".
     *
     * @param path a string to be formatted.
     *
     * @return the formatted string.
     */
    private fun imageNameFromPath(path: String): String {
        return imageNameWithPngExtension(name = path.substringAfterLast(delimiter = "/"))
    }

    /**
     * Formats a string, removing the char sequence after last occurrence of "."
     * and add ".png" in this place.
     *
     * @param name a string to be formated.
     *
     * @return the formatted string.
     */
    private fun imageNameWithPngExtension(name: String): String {
        return name.substringBeforeLast(delimiter = ".") + ".png"
    }

    /**
     * Extension to get a [Bitmap] from this [Uri].
     *
     * @return the [Bitmap] of this [Uri].
     */
    /* the warning in InputStream.close is a "false-positive" because this method runs on
    IO Dispatcher, apparently the IDE can't recognize a custom injected instance,
    so it's ok to use suppress BlockingMethodInNonBlockingContext here */
    @Suppress("BlockingMethodInNonBlockingContext")
    private suspend fun Uri.getBitmap(): Bitmap =
        withContext(context = coroutinesDispatchers.io) {
            val inputStream = context.contentResolver.openInputStream(this@getBitmap)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()

            bitmap
        }

    /**
     * Extension to resize a [Bitmap] keeping aspect ratio.
     *
     * @param maxWidth int value to limit the bitmap width. Default value is 700.
     * @param maxHeight int value to limit the bitmap height. Default value is 700.
     *
     * @return the new [Bitmap] resized.
     */
    private suspend fun Bitmap.resize(
        maxWidth: Int = 700,
        maxHeight: Int = 700
    ): Bitmap = withContext(context = coroutinesDispatchers.io) {
        val ratioBitmap = this@resize.width.toFloat() / this@resize.height.toFloat()
        val ratioMaxSize = maxWidth.toFloat() / maxHeight.toFloat()

        var finalWidth = maxWidth
        var finalHeight = maxHeight

        if (ratioMaxSize > ratioBitmap) finalWidth = (maxWidth.toFloat() * ratioBitmap).toInt()
        else finalHeight = (maxHeight.toFloat() / ratioBitmap).toInt()

        Bitmap.createScaledBitmap(this@resize, finalWidth, finalHeight, true)
    }

    /**
     * Extension to output this [Bitmap] in PNG format to a [File].
     *
     * @param file the [File] target of this Bitmap.
     */
    /* the warning in FileOutputStream is a "false-positive" because this method runs on
    IO Dispatcher, apparently the IDE can't recognize a custom injected instance,
    so it's ok to use suppress BlockingMethodInNonBlockingContext here */
    @Suppress("BlockingMethodInNonBlockingContext")
    private suspend fun Bitmap.outputImage(file: File) {
        withContext(context = coroutinesDispatchers.io) {
            val imageQuality = 100
            val outputStream = FileOutputStream(file)

            compress(Bitmap.CompressFormat.PNG, imageQuality, outputStream)

            outputStream.apply {
                flush()
                close()
            }
        }
    }

    override suspend fun saveImage(
        fileName: String,
        uri: Uri
    ): Bitmap? = withContext(context = coroutinesDispatchers.io) {
        return@withContext try {
            val imagesFilePath = File(imagesPath)
            if (!imagesFilePath.exists()) imagesFilePath.mkdirs()

            val bitmap = uri.getBitmap().run { resize() }

            val imageNameWithPngExtension =
                if (fileName.isNotBlank()) imageNameWithPngExtension(name = fileName)
                else imageNameFromPath(path = uri.pathSegments.last())

            val file = File(imagesFilePath, imageNameWithPngExtension)

            bitmap.outputImage(file = file)

            BitmapFactory.decodeFile(file.absolutePath)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun getImageBitmap(
        fileName: String
    ): Bitmap? = withContext(context = coroutinesDispatchers.io) {
        return@withContext try {
            val file = File("$imagesPath/$fileName")
            BitmapFactory.decodeFile(file.absolutePath)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}