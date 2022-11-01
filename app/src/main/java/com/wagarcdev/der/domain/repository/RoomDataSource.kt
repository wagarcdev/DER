package com.wagarcdev.der.domain.repository

import com.wagarcdev.der.domain.model.UserGoogle

interface RoomDataSource {

    fun createNewUserWithSignWithGoogle(userGoogle: UserGoogle)
    fun getAllGoogleUsers():List<UserGoogle>
}