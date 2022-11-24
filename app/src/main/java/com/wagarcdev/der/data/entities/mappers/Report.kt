package com.wagarcdev.der.data.entities.mappers

import com.wagarcdev.der.data.entities.ReportEntity
import com.wagarcdev.der.domain.model.Report

/**
 * Extension to convert [Report] in [ReportEntity].
 */
fun Report.asReportEntity() = ReportEntity(
    id = id,
    contract = contract,
    name = name,
    regionCode = regionCode,
    highway = highway,
    county = county,
    contractor = contractor,
    areaExtension = areaExtension,
    supervisor = supervisor,
    climate = climate,
    dayPeriod = dayPeriod,
    attachedImageNames = attachedImageNames
)

/**
 * Extension to convert [ReportEntity] in [Report].
 */
fun ReportEntity.asReport() = Report(
    id = id,
    contract = contract,
    name = name,
    regionCode = regionCode,
    highway = highway,
    county = county,
    contractor = contractor,
    areaExtension = areaExtension,
    supervisor = supervisor,
    climate = climate,
    dayPeriod = dayPeriod,
    attachedImageNames = attachedImageNames
)