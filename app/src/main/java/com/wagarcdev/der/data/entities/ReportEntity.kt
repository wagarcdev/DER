package com.wagarcdev.der.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wagarcdev.der.domain.model.Climate
import com.wagarcdev.der.domain.model.DayPeriod

@Entity(tableName = "reports")
class ReportEntity(
    @PrimaryKey(autoGenerate = false)
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