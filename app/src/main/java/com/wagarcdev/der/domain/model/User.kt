package com.wagarcdev.der.domain.model

/**
 * Model class that represents an user. The [id] must not be informed for an
 * operation to create a new user.
 */
data class User(
    val id: Int = 0,
    val name: String,
    val email: String,
    val password: String
)