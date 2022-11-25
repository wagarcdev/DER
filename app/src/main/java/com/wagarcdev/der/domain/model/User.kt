package com.wagarcdev.der.domain.model

data class User(
    val id: String = "0",
    val email: String? = "email",
    val username: String? = "user",
    val fullname: String? = "full",
    val displayName: String? = "display",
    val photoUrl: String? = "photo",
    val password: String? = "123",
    val isCommonUser: Boolean = false
)