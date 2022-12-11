package com.wagarcdev.der.presentation.screens.screen_register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wagarcdev.der.domain.model.User
import com.wagarcdev.der.domain.type.InputError
import com.wagarcdev.der.domain.type.InputResult
import com.wagarcdev.der.domain.usecase.InsertUserUseCase
import com.wagarcdev.der.domain.usecase.IsEmailAvailableUseCase
import com.wagarcdev.der.domain.usecase.SetCurrentUserIdUseCase
import com.wagarcdev.der.domain.usecase.ValidateEmailFieldUseCase
import com.wagarcdev.der.domain.usecase.ValidateSimpleFieldUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Sealed interface that represents one time events from view model to screen.
 */
sealed interface RegisterChannel {
    object UnavailableEmail : RegisterChannel
    object RegisterFailed : RegisterChannel
    object RegisterSuccessfully : RegisterChannel
}

/**
 * State holder of screen and view model.
 */
data class RegisterState(
    val name: String = "",
    val nameError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val repeatedPassword: String = "",
    val repeatedPasswordError: String? = null,
    val isPasswordVisible: Boolean = false,
    val isRepeatedPasswordVisible: Boolean = false
)

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val validateSimpleFieldUseCase: ValidateSimpleFieldUseCase,
    private val validateEmailFieldUseCase: ValidateEmailFieldUseCase,
    private val isEmailAvailableUseCase: IsEmailAvailableUseCase,
    private val insertUserUseCase: InsertUserUseCase,
    private val setCurrentUserIdUseCase: SetCurrentUserIdUseCase
) : ViewModel() {
    private val _registerState = MutableStateFlow(value = RegisterState())
    val registerState = _registerState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
        initialValue = _registerState.value
    )

    private val _channel = Channel<RegisterChannel>()
    val channel = _channel.receiveAsFlow()

    fun changeName(value: String) {
        _registerState.update { it.copy(name = value) }
    }

    fun changeEmail(value: String) {
        _registerState.update { it.copy(email = value) }
    }

    fun changePassword(value: String) {
        _registerState.update { it.copy(password = value) }
    }

    fun changeRepeatedPassword(value: String) {
        _registerState.update { it.copy(repeatedPassword = value) }
    }

    fun togglePasswordVisibility() {
        _registerState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun toggleRepeatedPasswordVisibility() {
        _registerState.update { it.copy(isRepeatedPasswordVisible = !it.isRepeatedPasswordVisible) }
    }

    fun signUpForEmailAndPassword() {
        _registerState.update { it.copy(name = it.name.trim()) }

        val nameResult = validateSimpleFieldUseCase(
            string = _registerState.value.name
        )

        val emailResult = validateEmailFieldUseCase(
            string = _registerState.value.email
        )

        val passwordResult = validateSimpleFieldUseCase(
            string = _registerState.value.password,
            minChar = 4
        )

        _registerState.update { state ->
            state.copy(
                nameError = when (nameResult) {
                    InputResult.Success -> null
                    is InputResult.Error -> when (nameResult.inputError) {
                        InputError.FieldEmpty -> "Name cannot be empty"
                        else -> null
                    }
                },
                emailError = when (emailResult) {
                    InputResult.Success -> null
                    is InputResult.Error -> when (emailResult.inputError) {
                        InputError.FieldEmpty -> "Email cannot be empty!"
                        InputError.FieldInvalid -> "This is not a valid email"
                        else -> null
                    }
                },
                passwordError = when (passwordResult) {
                    InputResult.Success -> null
                    is InputResult.Error -> when (passwordResult.inputError) {
                        InputError.FieldEmpty -> "Password cannot be empty"
                        InputError.FieldLessMinCharacters -> "Please use at least 4 chars"
                        else -> null
                    }
                },
                repeatedPasswordError = if (state.password == state.repeatedPassword) null else {
                    "The entered passwords don't match"
                }
            )
        }

        listOf(
            nameResult,
            emailResult,
            passwordResult
        ).any { inputResult ->
            inputResult is InputResult.Error
        }.also { hasError ->
            if (hasError) return
        }

        if (_registerState.value.repeatedPasswordError != null) return

        viewModelScope.launch {
            val isEmailAvailable = isEmailAvailableUseCase(email = _registerState.value.email)
            if (!isEmailAvailable) {
                _channel.send(element = RegisterChannel.UnavailableEmail)
                return@launch
            }

            val createdUserId = insertUserUseCase(
                user = User(
                    name = _registerState.value.name,
                    email = _registerState.value.email,
                    password = _registerState.value.password
                )
            )

            if (createdUserId > 0) {
                setCurrentUserIdUseCase(userId = createdUserId)
                _channel.send(element = RegisterChannel.RegisterSuccessfully)
            } else _channel.send(element = RegisterChannel.RegisterFailed)
        }
    }
}