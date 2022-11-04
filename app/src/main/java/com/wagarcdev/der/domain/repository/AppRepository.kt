package com.wagarcdev.der.domain.repository

import com.wagarcdev.der.domain.model.MyObject

interface AppRepository {

    suspend fun updateMyObject(myObject: MyObject)

    suspend fun getMyObjectById(myObjectId: Long): MyObject

    suspend fun deleteAllMyObjects()
}