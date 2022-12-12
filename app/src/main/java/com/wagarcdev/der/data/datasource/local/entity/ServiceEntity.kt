package com.wagarcdev.der.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wagarcdev.der.domain.model.Services

@Entity(tableName = "Servicos")
class ServiceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val cod_report: Int,
    val estrada_ponto_inicial: Int,
    val estrada_ponto_final: Int,
    val lado_pista: String,
    val desc_servico: String?,
    val comprimento: Int,
    val largura: Float,
    val altura: Float
) {
    companion object {
        fun fromModelToEntity(services: Services) = ServiceEntity(
            services.id,
            services.cod_report,
            services.estrada_ponto_inicial,
            services.estrada_ponto_final,
            services.lado_pista,
            services.desc_servico,
            services.comprimento,
            services.largura,
            services.altura
        )
    }

    fun fromEntityToModel() = Services(
        id,
        cod_report,
        estrada_ponto_inicial,
        estrada_ponto_final,
        lado_pista,
        desc_servico,
        comprimento,
        largura,
        altura
    )
}