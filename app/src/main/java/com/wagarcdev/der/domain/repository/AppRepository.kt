package com.wagarcdev.der.domain.repository

import com.wagarcdev.compose_mvvm_empty_project.domain.model.MyObject
import com.wagarcdev.der.data.entities.UserGoogleEntity

interface AppRepository {

    suspend fun createGoogleUser(userGoogleEntity: UserGoogleEntity)

    suspend fun updateMyObject(myObject: MyObject)

    suspend fun getMyObjectById(myObjectId: Long): MyObject

    suspend fun getAllGoogleUsers(): List<UserGoogleEntity>

    suspend fun deleteAllMyObjects()
}