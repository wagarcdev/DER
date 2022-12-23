package com.wagarcdev.der.presentation.screens.screen_reports

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wagarcdev.der.data.datasource.local.contracts
import com.wagarcdev.der.domain.model.Contract
import com.wagarcdev.der.domain.model.Report
import com.wagarcdev.der.domain.usecase.GetReportsForContractStreamUseCase
import com.wagarcdev.der.presentation.navigation.graphs.AppScreens
import com.wagarcdev.der.presentation.navigation.graphs.AppScreens.Contracts.contractIdKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * State holder of screen and view model.
 */
sealed interface ReportsState {
    object Loading : ReportsState

    data class Error(
        val message: String,
        val hasReload: Boolean
    ) : ReportsState

    data class Success(
        val contract: Contract,
        val reports: List<Report>
    ) : ReportsState
}

@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getReportsForContractStreamUseCase: GetReportsForContractStreamUseCase
) : ViewModel() {
    private val _reportsState: MutableStateFlow<ReportsState> = MutableStateFlow(
        value = ReportsState.Loading
    )

    val reportsState: StateFlow<ReportsState> = _reportsState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
        initialValue = _reportsState.value
    )

    init {
        getContractData()
    }

    private fun getContractData() {
        _reportsState.update { ReportsState.Loading }

        val contractId = savedStateHandle.get<String>(contractIdKey)
        if (contractId == null) {
            _reportsState.update {
                ReportsState.Error(
                    message = "We could not find the requested contract id",
                    hasReload = false
                )
            }
            return
        }

        viewModelScope.launch {
            delay(timeMillis = 500) // FAKE DELAY TO TEST IF ITS WORKING NICE FOR NOW

            // this will come from firebase later
            val contract = contracts.firstOrNull { it.number == contractId }
            if (contract == null) {
                _reportsState.update {
                    ReportsState.Error(
                        message = "Unable to find the corresponding contract",
                        hasReload = true
                    )
                }
                return@launch
            }

            getReportsForContractStreamUseCase(
                contractNumber = contract.number
            ).collectLatest { reports ->
                _reportsState.update {
                    ReportsState.Success(
                        contract = contract,
                        reports = reports.sortedBy { it.name.lowercase() }
                    )
                }
            }
        }
    }

    fun reloadContractData() {
        getContractData()
    }

}