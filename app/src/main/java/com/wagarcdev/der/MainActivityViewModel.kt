package com.wagarcdev.der

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wagarcdev.der.domain.usecase.GetCurrentUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * State holder of activity and view model.
 */
sealed interface MainActivityState {
    object Loading : MainActivityState

    data class Success(
        val currentUserId: Int = 0
    ) : MainActivityState
}

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase
) : ViewModel() {
    private val _mainActivityState: MutableStateFlow<MainActivityState> =
        MutableStateFlow(value = MainActivityState.Loading)

    val mainActivityState: StateFlow<MainActivityState> = _mainActivityState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
        initialValue = _mainActivityState.value
    )

    init {
        _mainActivityState.update { MainActivityState.Loading }

        viewModelScope.launch {
            // delay(timeMillis = 3000)
            val currentUserId = getCurrentUserIdUseCase()

            _mainActivityState.update {
                MainActivityState.Success(currentUserId = currentUserId)
            }
        }
    }
}