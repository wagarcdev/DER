package com.wagarcdev.der.presentation.screens.screen_register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wagarcdev.der.domain.type.InputError
import com.wagarcdev.der.domain.type.InputResult
import com.wagarcdev.der.domain.usecase.ValidateEmailFieldUseCase
import com.wagarcdev.der.domain.usecase.ValidateSimpleFieldUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * State holder of screen and view model.
 */
data class RegisterState(
    val name: String = "",
    val nameError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val username: String = "",
    val usernameError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val repeatedPassword: String = "",
    val repeatedPasswordError: String? = null
)

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val validateSimpleFieldUseCase: ValidateSimpleFieldUseCase,
    private val validateEmailFieldUseCase: ValidateEmailFieldUseCase
) : ViewModel() {
    private val _registerState = MutableStateFlow(value = RegisterState())

    val registerState = _registerState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
        initialValue = _registerState.value
    )

    fun changeName(value: String) {
        _registerState.update { it.copy(name = value) }
    }

    fun changeEmail(value: String) {
        _registerState.update { it.copy(email = value) }
    }

    fun changeUsername(value: String) {
        _registerState.update { it.copy(username = value) }
    }

    fun changePassword(value: String) {
        _registerState.update { it.copy(password = value) }
    }

    fun changeRepeatedPassword(value: String) {
        _registerState.update { it.copy(repeatedPassword = value) }
    }

    fun signUpForEmailAndPassword() {
        _registerState.update { it.copy(name = it.name.trim()) }

        val nameResult = validateSimpleFieldUseCase(
            string = _registerState.value.name
        )

        val emailResult = validateEmailFieldUseCase(
            string = _registerState.value.email
        )

        val usernameResult = validateSimpleFieldUseCase(
            string = _registerState.value.username
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
                usernameError = when (usernameResult) {
                    InputResult.Success -> null
                    is InputResult.Error -> when (usernameResult.inputError) {
                        InputError.FieldEmpty -> "Username cannot be empty"
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
    }
}