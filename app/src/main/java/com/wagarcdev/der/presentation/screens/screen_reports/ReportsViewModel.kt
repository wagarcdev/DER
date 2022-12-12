package com.wagarcdev.der.presentation.screens.screen_reports

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wagarcdev.der.data.datasource.local.contracts
import com.wagarcdev.der.domain.model.Contract
import com.wagarcdev.der.domain.model.Report
import com.wagarcdev.der.presentation.navigation.graphs.AppScreens.Contracts.contractIdKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * State holder of screen and view model.
 */
data class ReportsState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val contract: Contract? = null,
    val reports: List<Report> = emptyList()
)

@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _reportsState = MutableStateFlow(value = ReportsState())
    val reportsState = _reportsState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
        initialValue = _reportsState.value
    )

    init {
        getContractData()
    }

    private fun getContractData() {
        _reportsState.update { it.copy(isLoading = true) }

        val contractId = savedStateHandle.get<String>(contractIdKey)

        if (contractId != null) {
            // this will come from firebase later
            val contract = contracts.firstOrNull { contract ->
                contract.number == contractId
            }

            _reportsState.update { state ->
                state.copy(
                    isLoading = false,
                    errorMessage = null,
                    contract = contract
                )
            }
        } else _reportsState.update { state ->
            state.copy(
                isLoading = false,
                errorMessage = "Unable to retrieve contract id"
            )
        }
    }

    // todo load reports for contract etc...
}