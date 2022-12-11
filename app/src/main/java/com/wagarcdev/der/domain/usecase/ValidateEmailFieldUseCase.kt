package com.wagarcdev.der.domain.usecase

import android.util.Patterns
import com.wagarcdev.der.domain.type.InputError
import com.wagarcdev.der.domain.type.InputResult
import javax.inject.Inject

/**
 * Use case to validate an email.
 */
interface ValidateEmailFieldUseCase {
    /**
     * @param string the email [String] to be validated.
     *
     * @return [InputResult].
     */
    operator fun invoke(string: String): InputResult
}

/**
 * Implementation of [ValidateEmailFieldUseCase].
 */
class ValidateEmailFieldUseCaseImpl @Inject constructor() : ValidateEmailFieldUseCase {
    override fun invoke(string: String): InputResult {
        if (string.isBlank()) return InputResult.Error(
            inputError = InputError.FieldEmpty
        )

        if (!Patterns.EMAIL_ADDRESS.matcher(string).matches())
            return InputResult.Error(inputError = InputError.FieldInvalid)

        return InputResult.Success
    }
}