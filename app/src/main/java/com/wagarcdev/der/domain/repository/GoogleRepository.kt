package com.wagarcdev.der.domain.repository

import com.wagarcdev.der.domain.model.User

// TODO(GIVE A BETTER NAME)
interface GoogleRepository {
    fun createNewUserWithSignWithGoogle(users: User)

    fun getAllGoogleUsers(): List<User>
}