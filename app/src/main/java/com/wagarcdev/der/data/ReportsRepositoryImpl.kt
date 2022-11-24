package com.wagarcdev.der.data

import com.wagarcdev.der.data.entities.mappers.asReportEntity
import com.wagarcdev.der.data.local.AppDatabaseDAO
import com.wagarcdev.der.domain.model.Report
import com.wagarcdev.der.domain.repository.ReportsRepository
import javax.inject.Inject

class ReportsRepositoryImpl @Inject constructor(
    private val dao: AppDatabaseDAO
) : ReportsRepository {
    override suspend fun createNewReport(
        report: Report
    ) = dao.createNewReport(reportEntity = report.asReportEntity())
}