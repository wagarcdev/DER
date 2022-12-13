package com.wagarcdev.der.domain.usecase

import com.wagarcdev.der.data.repository.ReportsRepository
import com.wagarcdev.der.domain.model.Report
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case to get an stream data of report by id.
 */
interface GetReportByIdStreamUseCase {
    /**
     * @param id the [String] id of report to be get.
     *
     * @return [Flow] of [Report] or null if there is no report found for the provided [id].
     */
    operator fun invoke(id: String): Flow<Report?>
}

/**
 * Implementation of [GetReportByIdStreamUseCase] that uses [ReportsRepository].
 */
class GetReportByIdStreamUseCaseImpl @Inject constructor(
    private val reportsRepository: ReportsRepository
) : GetReportByIdStreamUseCase {
    override fun invoke(id: String): Flow<Report?> =
        reportsRepository.getReportByIdStream(id = id)
}