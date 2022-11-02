package com.wagarcdev.der.domain.repository

import com.wagarcdev.der.domain.model.UserGoogle

interface GoogleRepository {

    fun createNewUserWithSignWithGoogle(userGoogle: UserGoogle)
    fun getAllGoogleUsers():List<UserGoogle>
}