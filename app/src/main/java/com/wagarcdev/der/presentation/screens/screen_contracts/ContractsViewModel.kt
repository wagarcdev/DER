package com.wagarcdev.der.presentation.screens.screen_contracts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wagarcdev.der.domain.usecase.GetCurrentUserIdUseCase
import com.wagarcdev.der.domain.usecase.GetUserByIdStreamUseCase
import com.wagarcdev.der.domain.usecase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ContractsChannel {
    object RedirectToLogin : ContractsChannel
}

/**
 * State holder of screen and view model.
 */
data class ContractsState(
    val userId: Int = 0,
    val userName: String = ""
)

@HiltViewModel
class ContractsViewModel @Inject constructor(
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val getUserByIdStreamUseCase: GetUserByIdStreamUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {
    private val _contractsState = MutableStateFlow(value = ContractsState())
    val contractsState = _contractsState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = _contractsState.value
    )

    private val _channel = Channel<ContractsChannel>()
    val channel = _channel.receiveAsFlow()

    init {
        watchUserInfoStream()
    }

    private fun watchUserInfoStream() {
        viewModelScope.launch {
            val currentUserId = getCurrentUserIdUseCase()

            if (currentUserId == 0) {
                _channel.send(element = ContractsChannel.RedirectToLogin)
                return@launch
            }

            getUserByIdStreamUseCase(id = currentUserId).collectLatest { nullableUser ->
                nullableUser?.let { user ->
                    _contractsState.update { state ->
                        state.copy(userId = user.id, userName = user.name)
                    }
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            if (logoutUseCase()) _channel.send(element = ContractsChannel.RedirectToLogin)
        }
    }
}