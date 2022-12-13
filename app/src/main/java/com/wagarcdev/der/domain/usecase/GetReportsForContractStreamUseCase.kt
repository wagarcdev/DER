package com.wagarcdev.der.domain.usecase

import com.wagarcdev.der.data.repository.ReportsRepository
import com.wagarcdev.der.domain.model.Report
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case to get an stream data of list of report for an contract number.
 */
interface GetReportsForContractStreamUseCase {
    /**
     * @param contractNumber the [String] that represents the contract number.
     *
     * @return [Flow] of list of [Report]. This list will be empty if no reports are saved
     * for this contract number.
     */
    operator fun invoke(contractNumber: String): Flow<List<Report>>
}

/**
 * Implementation of [GetReportsForContractStreamUseCase] that uses [ReportsRepository].
 */
class GetReportsForContractStreamUseCaseImpl @Inject constructor(
    private val reportsRepository: ReportsRepository
) : GetReportsForContractStreamUseCase {
    override fun invoke(contractNumber: String): Flow<List<Report>> =
        reportsRepository.getReportsForContractStream(contractNumber = contractNumber)
}