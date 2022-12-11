package com.wagarcdev.der.domain.model

/**
 * Model class that represents an report.
 */
data class Report(
    val id: Int,
    val contract: String,
    val name: String,
    val regionCode: String,
    val highway: String,
    val county: String,
    val contractor: String,
    val areaExtension: String,
    val supervisor: String,
    val climate: Climate,
    val dayPeriod: DayPeriod,
    val attachedImageNames: List<String>
)