package com.wagarcdev.der.presentation.screens.screen_create_or_edit_report

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wagarcdev.der.domain.model.Climate
import com.wagarcdev.der.domain.model.DayPeriod
import com.wagarcdev.der.domain.model.Report
import com.wagarcdev.der.domain.usecase.GetReportByIdStreamUseCase
import com.wagarcdev.der.domain.usecase.InsertReportUseCase
import com.wagarcdev.der.presentation.navigation.graphs.AppScreens.Contracts.contractIdKey
import com.wagarcdev.der.presentation.navigation.graphs.AppScreens.Contracts.reportIdKey
import com.wagarcdev.der.utils.CreatePdf
import com.wagarcdev.der.utils.ImageManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Data class that represents the state of screen.
 */
data class CreateOrEditReportScreenState(
    val report: Report
)

/**
 * Data class that represents the state of the view model.
 */
private data class CreateOrEditReportViewModelState(
    val id: String = "",
    val contractNumber: String = "",
    val name: String = "",
    val regionCode: String = "",
    val highway: String = "",
    val county: String = "",
    val contractor: String = "",
    val areaExtension: String = "",
    val supervisor: String = "",
    val climate: Climate = Climate.Unspecified,
    val dayPeriod: DayPeriod = DayPeriod.Unspecified,
    val attachedImageNames: List<String> = emptyList()
) {
    fun asScreenState() = CreateOrEditReportScreenState(
        report = Report(
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
    )
}

@HiltViewModel
class CreateOrEditReportViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getReportByIdStreamUseCase: GetReportByIdStreamUseCase,
    private val insertReportUseCase: InsertReportUseCase,
    private val imageManager: ImageManager,
    private val createPdf: CreatePdf
) : ViewModel() {
    private val viewModelState: MutableStateFlow<CreateOrEditReportViewModelState> =
        MutableStateFlow(value = CreateOrEditReportViewModelState())

    val screenState: StateFlow<CreateOrEditReportScreenState> =
        viewModelState.map { it.asScreenState() }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = viewModelState.value.asScreenState()
        )

    init {
        initializeFormData()
    }

    private fun initializeFormData() {
        val contractId = savedStateHandle.get<String>(contractIdKey)
        val reportId = savedStateHandle.get<String>(reportIdKey)

        when {
            !contractId.isNullOrBlank() -> {
                val currentTime = System.currentTimeMillis()

                viewModelState.update { state ->
                    state.copy(
                        id = "$currentTime-$contractId",
                        contractNumber = contractId
                    )
                }
            }
            !reportId.isNullOrBlank() -> {
                viewModelScope.launch {
                    getReportByIdStreamUseCase(id = reportId).collectLatest { report ->
                        report?.let {
                            viewModelState.update { state ->
                                state.copy(
                                    id = reportId,
                                    contractNumber = it.contractNumber,
                                    name = it.name,
                                    regionCode = it.regionCode,
                                    highway = it.highway,
                                    county = it.county,
                                    contractor = it.contractor,
                                    areaExtension = it.areaExtension,
                                    supervisor = it.supervisor,
                                    climate = it.climate,
                                    dayPeriod = it.dayPeriod,
                                    attachedImageNames = it.attachedImageNames
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun changeName(value: String) {
        viewModelState.update { it.copy(name = value) }
        saveReport()
    }

    fun changeRegionCode(value: String) {
        viewModelState.update { it.copy(regionCode = value) }
        saveReport()
    }

    fun changeHighway(value: String) {
        viewModelState.update { it.copy(highway = value) }
        saveReport()
    }

    fun changeCounty(value: String) {
        viewModelState.update { it.copy(county = value) }
        saveReport()
    }

    fun changeContractor(value: String) {
        viewModelState.update { it.copy(contractor = value) }
        saveReport()
    }

    fun changeAreaExtension(value: String) {
        viewModelState.update { it.copy(areaExtension = value) }
        saveReport()
    }

    fun changeSupervisor(value: String) {
        viewModelState.update { it.copy(supervisor = value) }
        saveReport()
    }

    fun changeClimate(value: Climate) {
        viewModelState.update { it.copy(climate = value) }
        saveReport()
    }

    fun changeDayPeriod(value: DayPeriod) {
        viewModelState.update { it.copy(dayPeriod = value) }
        saveReport()
    }

    fun addImage(uri: Uri) {
        viewModelScope.launch {
            val result = imageManager.saveImage(uri = uri)

            result?.let { pair ->
                val imagesUriMutable =
                    viewModelState.value.attachedImageNames.toMutableList().apply {
                        add(element = pair.first)
                    }

                viewModelState.update { it.copy(attachedImageNames = imagesUriMutable.toList()) }
            }

            saveReport()
        }
    }

    fun removeImage(index: Int) {
        val imagesUriMutable = viewModelState.value.attachedImageNames.toMutableList().apply {
            removeAt(index = index)
        }

        viewModelState.update { it.copy(attachedImageNames = imagesUriMutable.toList()) }
        saveReport()
    }

    private fun saveReport() {
        viewModelScope.launch {
            insertReportUseCase(report = screenState.value.report)
        }
    }
}