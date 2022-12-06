package com.wagarcdev.der.domain.usecase

import android.util.Patterns
import com.wagarcdev.der.domain.type.InputError
import com.wagarcdev.der.domain.type.InputResult
import javax.inject.Inject

interface ValidateEmailFieldUseCase {
    operator fun invoke(string: String): InputResult
}

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