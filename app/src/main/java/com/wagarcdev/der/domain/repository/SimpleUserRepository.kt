package com.wagarcdev.der.domain.repository

import com.wagarcdev.der.domain.model.User

// TODO(GIVE A BETTER NAME)
interface SimpleUserRepository {
    suspend fun createNewSimpleUser(user: User)

    suspend fun getAllUsers(): List<User>

    suspend fun validateLogin(isCommonUser: Boolean, username: String): String

    suspend fun getUserId(isCommonUser: Boolean, username: String): String

    suspend fun getUserById(id: String): User
}