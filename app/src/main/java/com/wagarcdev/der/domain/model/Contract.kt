package com.wagarcdev.der.domain.model

data class Contract(
    val number: String,
    val company: String,
    val companyLogo: Int,
    val road: String,
    val serviceDescription: String,
    val block: Int,
    val plot: Int,
    val startKm: Int,
    val startAdjustInMeters: Int,
    val endKm: Int,
    val endAdjustInMeters: Int,
    val city: String,
) {
    val extensionInMeters: Int
        get() = ((endKm * 1000) + endAdjustInMeters) - ((startKm * 1000) + startAdjustInMeters)
}
