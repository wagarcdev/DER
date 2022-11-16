package com.wagarcdev.der.domain.model

data class Report(
    val id: Int,
    val name: String?,
    val div_regional : String?,
    val rodovia:String?,
    val objeto: String?,
    val municipio: String?,
    val empreiteira:String?,
    val contrato:String?,
    val extensao:Int,
    val fiscal_campo:String,
    val condicoesClimaticas:String
)