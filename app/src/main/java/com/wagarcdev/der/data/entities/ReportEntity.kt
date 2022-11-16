package com.wagarcdev.der.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wagarcdev.der.domain.model.Report

@Entity(tableName = "Relatorios")
class ReportEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String?,
    val div_regional: String?,
    val rodovia: String?,
    val objeto: String?,
    val municipio: String?,
    val empreiteira: String?,
    val contrato: String?,
    val extensao: Int,
    val fiscal_campo: String,
    val condicoesClimaticas: String
) {
    companion object {
        fun fromModelToEntity(report: Report) = ReportEntity(
            report.id,
            report.name,
            report.div_regional,
            report.rodovia,
            report.objeto,
            report.municipio,
            report.empreiteira,
            report.contrato,
            report.extensao,
            report.fiscal_campo,
            report.condicoesClimaticas
        )
    }

    fun fromEntityToModel() = Report(
        id,
        name,
        div_regional,
        rodovia,
        objeto,
        municipio,
        empreiteira,
        contrato,
        extensao,
        fiscal_campo,
        condicoesClimaticas
    )
}