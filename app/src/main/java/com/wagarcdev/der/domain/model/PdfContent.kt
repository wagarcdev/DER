package com.wagarcdev.der.domain.model

import android.graphics.Bitmap

/**
 * Model class for all required information to create a PDP.
 */
data class PdfContent(
    val fileName: String,
    val displayTexts: List<DisplayText>,
    val bitmaps: List<Bitmap>
)