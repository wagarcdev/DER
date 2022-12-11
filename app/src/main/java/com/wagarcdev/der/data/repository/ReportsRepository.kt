package com.wagarcdev.der.data.repository

import com.wagarcdev.der.data.datasource.local.dao.ReportsDao
import com.wagarcdev.der.data.datasource.local.entity.ReportEntity
import com.wagarcdev.der.domain.model.Report
import javax.inject.Inject

/**
 * Repository interface for [Report].
 */
interface ReportsRepository {
    /**
     * Insert an report.
     *
     * @param report the [Report] to be inserted.
     *
     * @return an [Int] value that represents the current id of the inserted report.
     * -1 is returned if this operation fails.
     */
    suspend fun insertReport(report: Report): Int
}

/**
 * Implementation of [ReportsRepository] that uses [ReportsDao].
 */
class ReportsRepositoryImpl @Inject constructor(
    private val reportsDao: ReportsDao
) : ReportsRepository {
    override suspend fun insertReport(
        report: Report
    ): Int = reportsDao.insertReportAndReplace(
        reportEntity = report.asReportEntity()
    ).toInt()

    /**
     * Extension to map an [Report] to [ReportEntity].
     */
    private fun Report.asReportEntity() = ReportEntity(
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
}