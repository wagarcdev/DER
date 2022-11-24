package com.wagarcdev.der.presentation.screens.screen_create_report

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wagarcdev.der.domain.model.Climate
import com.wagarcdev.der.domain.model.DayPeriod
import com.wagarcdev.der.domain.model.Report
import com.wagarcdev.der.domain.repository.ReportsRepository
import com.wagarcdev.der.utils.CreatePdf
import com.wagarcdev.der.utils.ImageManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Data class that represents the state of screen.
 */
data class CreateReportScreenState(
    val report: Report
)

/**
 * Data class that represents the state of the view model.
 */
private data class CreateReportViewModelState(
    val id: Int = 0,
    val contract: String = "",
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
    fun asScreenState() = CreateReportScreenState(
        report = Report(
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
    )
}

@HiltViewModel
class CreateReportViewModel @Inject constructor(
    private val reportsRepository: ReportsRepository,
    private val imageManager: ImageManager,
    private val createPdf: CreatePdf
) : ViewModel() {
    private val viewModelState = MutableStateFlow(value = CreateReportViewModelState())

    val screenState = viewModelState.map { it.asScreenState() }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
        initialValue = viewModelState.value.asScreenState()
    )

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
            reportsRepository.createNewReport(report = screenState.value.report)
        }
    }
}