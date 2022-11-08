package com.wagarcdev.der.domain.repository

import com.wagarcdev.der.domain.model.Users

interface GoogleRepository {

    fun createNewUserWithSignWithGoogle(users: Users)
    fun getAllGoogleUsers():List<Users>
}