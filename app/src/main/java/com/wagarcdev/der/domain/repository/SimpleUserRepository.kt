package com.wagarcdev.der.domain.repository

import com.wagarcdev.der.domain.model.Users

interface SimpleUserRepository {


    fun createNewSimpleUser(user: Users)
    fun getAllUsers():List<Users>
}