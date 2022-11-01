package com.wagarcdev.der.domain.model

data class Contract(
    val number: String,
    val company: String,
    val companyLogo: Int,
    val road: String,
    val serviceDescription: String,
    val site: String,
    val sections: String,
    val extension: String,
    val city: String
)
