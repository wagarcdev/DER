package com.wagarcdev.der.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wagarcdev.der.domain.model.Climate
import com.wagarcdev.der.domain.model.DayPeriod
import com.wagarcdev.der.domain.model.Report

/**
 * Room entity for [Report].
 */
@Entity(tableName = "reports")
class ReportEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val contractNumber: String,
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