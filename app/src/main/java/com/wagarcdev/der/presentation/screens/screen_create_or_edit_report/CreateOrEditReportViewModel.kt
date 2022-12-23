package com.wagarcdev.der.presentation.screens.screen_create_or_edit_report

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wagarcdev.der.domain.model.*
import com.wagarcdev.der.domain.usecase.GetReportByIdStreamUseCase
import com.wagarcdev.der.domain.usecase.InsertReportUseCase
import com.wagarcdev.der.presentation.navigation.graphs.AppScreens.Contracts.contractIdKey
import com.wagarcdev.der.presentation.navigation.graphs.AppScreens.Contracts.reportIdKey
import com.wagarcdev.der.utils.Constants
import com.wagarcdev.der.utils.CreatePdf
import com.wagarcdev.der.utils.ImageManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.File
import java.util.*
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

    private val _fileState: MutableStateFlow<File?> = MutableStateFlow(value = null)
    val fileState: StateFlow<File?> = _fileState

    private val _textButton: MutableStateFlow<String> = MutableStateFlow(value = Constants.CREATE_PDF)
    val textButton: StateFlow<String> = _textButton

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
                _textButton.value = Constants.SHARE_PDF
                createPdf()
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

    fun createPdf() {
        viewModelScope.launch {
            val fileName = viewModelState.value.name.takeIf { it.isNotBlank() } ?: "test"

            val bitmaps = viewModelState.value.attachedImageNames.mapNotNull {
                imageManager.getImageBitmap(fileName = it)
            }

            val pdfContent = PdfContent(
                fileName = fileName,
                displayTexts = getDisplayTexts(),
                bitmaps = bitmaps
            )
            val file = createPdf.invoke(pdfContent = pdfContent)
            _fileState.value = file

        }
    }
    // todo improvement needed
    private fun getDisplayTexts(): List<DisplayText> {
        val nameSection = if (viewModelState.value.name.isNotBlank()) {
            listOf(
                DisplayText(
                    text = "Name:",
                    textConfig = defaultTitleTextConfig
                ),
                DisplayText(
                    text = viewModelState.value.name,
                    textConfig = defaultContentTextConfig
                )
            )
        } else emptyList()

        val regionCodeSection = if (viewModelState.value.regionCode.isNotBlank()) {
            listOf(
                DisplayText(
                    text = "Region code:",
                    textConfig = defaultTitleTextConfig
                ),
                DisplayText(
                    text = viewModelState.value.regionCode,
                    textConfig = defaultContentTextConfig
                )
            )
        } else emptyList()

        val highwaySection = if (viewModelState.value.highway.isNotBlank()) {
            listOf(
                DisplayText(
                    text = "Highway:",
                    textConfig = defaultTitleTextConfig
                ),
                DisplayText(
                    text = viewModelState.value.highway,
                    textConfig = defaultContentTextConfig
                )
            )
        } else emptyList()

        val countySection = if (viewModelState.value.county.isNotBlank()) {
            listOf(
                DisplayText(
                    text = "County:",
                    textConfig = defaultTitleTextConfig
                ),
                DisplayText(
                    text = viewModelState.value.county,
                    textConfig = defaultContentTextConfig
                )
            )
        } else emptyList()

        val contractorSection = if (viewModelState.value.contractor.isNotBlank()) {
            listOf(
                DisplayText(
                    text = "Contractor:",
                    textConfig = defaultTitleTextConfig
                ),
                DisplayText(
                    text = viewModelState.value.contractor,
                    textConfig = defaultContentTextConfig
                )
            )
        } else emptyList()

        val areaExtensionSection = if (viewModelState.value.areaExtension.isNotBlank()) {
            listOf(
                DisplayText(
                    text = "Area extension:",
                    textConfig = defaultTitleTextConfig
                ),
                DisplayText(
                    text = viewModelState.value.areaExtension,
                    textConfig = defaultContentTextConfig
                )
            )
        } else emptyList()

        val supervisorSection = if (viewModelState.value.supervisor.isNotBlank()) {
            listOf(
                DisplayText(
                    text = "Supervisor:",
                    textConfig = defaultTitleTextConfig
                ),
                DisplayText(
                    text = viewModelState.value.supervisor,
                    textConfig = defaultContentTextConfig
                )
            )
        } else emptyList()

        val climateSection = listOf(
            DisplayText(
                text = "Climate:",
                textConfig = defaultTitleTextConfig
            ),
            DisplayText(
                text = viewModelState.value.climate.name,
                textConfig = defaultContentTextConfig
            )
        )

        val dayPeriodSection = listOf(
            DisplayText(
                text = "Day period:",
                textConfig = defaultTitleTextConfig
            ),
            DisplayText(
                text = viewModelState.value.dayPeriod.name,
                textConfig = defaultContentTextConfig
            )
        )

        return listOf(
            nameSection,
            regionCodeSection,
            highwaySection,
            countySection,
            contractorSection,
            areaExtensionSection,
            supervisorSection,
            climateSection,
            dayPeriodSection
        ).flatten()
    }

    private fun saveReport() {
        viewModelScope.launch {
            insertReportUseCase(report = screenState.value.report)
        }
    }
}