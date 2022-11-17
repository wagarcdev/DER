package com.wagarcdev.der.domain.model

import android.graphics.Typeface
import android.text.Layout
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

/**
 * Model class with style options for a text of PDF.
 */
data class TextConfig(
    val textTypeface: Typeface,
    val textAlign: Layout.Alignment,
    val textColor: Int,
    val textSize: Float,
    val bottomSpace: Float
)

val defaultHeaderTextConfig = TextConfig(
    textTypeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL),
    textAlign = Layout.Alignment.ALIGN_CENTER,
    textColor = Color(color = 0xFF222222).toArgb(),
    textSize = 18F,
    bottomSpace = 12F
)

val defaultTitleTextConfig = TextConfig(
    textTypeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD),
    textAlign = Layout.Alignment.ALIGN_NORMAL,
    textColor = Color(color = 0xFF222222).toArgb(),
    textSize = 14F,
    bottomSpace = 4F
)

val defaultContentTextConfig = TextConfig(
    textTypeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL),
    textAlign = Layout.Alignment.ALIGN_NORMAL,
    textColor = Color(color = 0xFF222222).toArgb(),
    textSize = 13F,
    bottomSpace = 4F
)