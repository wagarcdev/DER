package com.wagarcdev.der.domain.type

/**
 * Types of error for input.
 */
enum class InputError {
    FieldEmpty,
    FieldInvalid,
    FieldLessMinCharacters,
    FieldMoreMaxCharacters
}