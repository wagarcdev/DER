package com.wagarcdev.der.domain.repository

import com.wagarcdev.der.domain.model.SimpleUser

interface SimpleUserRepository {


    fun createNewSimpleUser(simpleUser: SimpleUser)
    fun getAllUsers():List<SimpleUser>
}