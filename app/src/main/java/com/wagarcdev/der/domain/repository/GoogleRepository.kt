package com.wagarcdev.der.domain.repository

import com.wagarcdev.der.domain.model.User

// TODO(GIVE A BETTER NAME)
interface GoogleRepository {
    suspend fun createNewUserWithSignWithGoogle(users: User)

    suspend fun getAllGoogleUsers(): List<User>

    suspend fun getGoogleUserById(idUser:String) : User
}