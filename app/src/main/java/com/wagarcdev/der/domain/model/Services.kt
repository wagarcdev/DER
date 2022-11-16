package com.wagarcdev.der.domain.model


data class Services(
    val id: Int,
    val cod_report: Int,
    val estrada_ponto_inicial : Int,
    val estrada_ponto_final : Int,
    val lado_pista: String,
    val desc_servico: String?,
    val comprimento:Int,
    val largura:Float,
    val altura:Float
)