package com.wagarcdev.der.domain.usecase

import com.wagarcdev.der.data.repository.ReportsRepository
import com.wagarcdev.der.domain.model.Report
import javax.inject.Inject

/**
 * Use case to insert an report.
 */
interface InsertReportUseCase {
    /**
     * @param report the [Report] to be inserted.
     *
     * @return an [Int] value that represents the current id of the inserted report.
     * -1 is returned if this operation fails.
     */
    suspend operator fun invoke(report: Report): Int
}

/**
 * Implementation of [InsertReportUseCase] that uses [ReportsRepository].
 */
class InsertReportUseCaseImpl @Inject constructor(
    private val reportsRepository: ReportsRepository
) : InsertReportUseCase {
    override suspend fun invoke(report: Report): Int =
        reportsRepository.insertReport(report = report)
}