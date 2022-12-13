package com.wagarcdev.der.data.repository

import com.wagarcdev.der.data.datasource.local.dao.ReportsDao
import com.wagarcdev.der.data.datasource.local.entity.ReportEntity
import com.wagarcdev.der.domain.model.Report
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Repository interface for [Report].
 */
interface ReportsRepository {
    /**
     * Get a data stream list of report for an contract number.
     *
     * @param contractNumber the [String] that represents the contract number.
     *
     * @return [Flow] of list of [Report]. This list will be empty if no reports are saved
     * for this contract number.
     */
    fun getReportsForContractStream(contractNumber: String): Flow<List<Report>>

    /**
     * Get a data stream of nullable report by id.
     *
     * @param id the [String] id of report to be get.
     *
     * @return [Flow] of [Report] or null if there is no report found for the provided [id].
     */
    fun getReportByIdStream(id: String): Flow<Report?>

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
    override fun getReportsForContractStream(
        contractNumber: String
    ): Flow<List<Report>> = reportsDao.getReportsForContractStream(
        contractNumber = contractNumber
    ).map { reportEntities ->
        reportEntities.asReports()
    }

    override fun getReportByIdStream(id: String): Flow<Report?> =
        reportsDao.getReportByIdStream(id = id).map { reportEntity ->
            reportEntity?.asReport()
        }

    override suspend fun insertReport(
        report: Report
    ): Int = reportsDao.insertReportAndReplace(
        reportEntity = report.asReportEntity()
    ).toInt()

    /**
     * Extension to map an [ReportEntity] to [Report].
     */
    private fun ReportEntity.asReport() = Report(
        id = id,
        contractNumber = contractNumber,
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
     * Extension to map an List of [ReportEntity] to a List of [Report].
     */
    private fun List<ReportEntity>.asReports() = map { reportEntity ->
        reportEntity.asReport()
    }

    /**
     * Extension to map an [Report] to [ReportEntity].
     */
    private fun Report.asReportEntity() = ReportEntity(
        id = id,
        contractNumber = contractNumber,
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