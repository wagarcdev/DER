package com.wagarcdev.der.presentation.screens.screen_create_report

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    val text: String,
    val textError: String?,
    val imagesBitmap: List<Bitmap>
)

/**
 * Data class that represents the state of the view model.
 */
private data class CreateReportViewModelState(
    val text: String = "",
    val textError: String? = null,
    val imagesBitmap: List<Bitmap> = emptyList()
) {
    fun asScreenState() = CreateReportScreenState(
        text = text,
        textError = textError,
        imagesBitmap = imagesBitmap
    )
}

@HiltViewModel
class CreateReportViewModel @Inject constructor(
    private val imageManager: ImageManager,
    private val createPdf: CreatePdf
) : ViewModel() {
    private val viewModelState = MutableStateFlow(value = CreateReportViewModelState())

    val screenState = viewModelState.map { it.asScreenState() }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
        initialValue = viewModelState.value.asScreenState()
    )

    fun changeText(value: String) {
        viewModelState.update { it.copy(text = value) }
    }

    fun addImage(uri: Uri) {
        viewModelScope.launch {
            val imageBitmap = imageManager.saveImage(uri = uri)

            imageBitmap?.let { bitmap ->
                val imagesUriMutable = viewModelState.value.imagesBitmap.toMutableList().apply {
                    add(element = bitmap)
                }

                viewModelState.update { it.copy(imagesBitmap = imagesUriMutable.toList()) }
            }
        }
    }

    fun removeImage(index: Int) {
        val imagesUriMutable = viewModelState.value.imagesBitmap.toMutableList().apply {
            removeAt(index = index)
        }

        viewModelState.update { it.copy(imagesBitmap = imagesUriMutable.toList()) }
    }
}