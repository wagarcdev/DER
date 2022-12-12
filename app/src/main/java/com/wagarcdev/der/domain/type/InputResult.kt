package com.wagarcdev.der.domain.type

/**
 * Types of result for a input validation.
 */
sealed interface InputResult {
    object Success : InputResult

    data class Error(
        val inputError: InputError
    ) : InputResult
}