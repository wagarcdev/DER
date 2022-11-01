package com.wagarcdev.der.domain.repository

import com.wagarcdev.compose_mvvm_empty_project.domain.model.MyObject

interface AppRepository {

    suspend fun addMyObject(myObject: MyObject): Long?

    suspend fun updateMyObject(myObject: MyObject)

    suspend fun getMyObjectById(myObjectId: Long): MyObject

    suspend fun getAllMyObjects(): List<MyObject>

    suspend fun deleteAllMyObjects()
}