package com.wagarcdev.der.domain.repository

import com.wagarcdev.der.domain.model.User

// TODO(GIVE A BETTER NAME)
interface SimpleUserRepository {
    fun createNewSimpleUser(user: User)

    fun getAllUsers(): List<User>

    fun validateLogin(isCommonUser: Boolean, username: String): String

    fun getUserId(isCommonUser: Boolean, username: String): String

    fun getUserById(id: String): User
}