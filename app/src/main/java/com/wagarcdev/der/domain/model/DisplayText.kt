package com.wagarcdev.der.domain.model

/**
 * Model class with a text and our style to be displayed on PDF.
 */
data class DisplayText(
    val text: String,
    val textConfig: TextConfig
)