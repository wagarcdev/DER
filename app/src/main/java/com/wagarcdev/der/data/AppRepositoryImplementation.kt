package com.wagarcdev.compose_mvvm_empty_project.data

import com.wagarcdev.compose_mvvm_empty_project.domain.model.MyObject
import com.wagarcdev.der.data.entities.UserGoogleEntity
import com.wagarcdev.der.data.local.AppDatabaseDAO
import com.wagarcdev.der.domain.repository.AppRepository

class AppRepositoryImplementation(
    private val dao: AppDatabaseDAO
) : AppRepository {

    override suspend fun getMyObjectById(myObjectId: Long): MyObject {
        return dao.getMyObjectById(myObjectId)
    }

    override suspend fun createGoogleUser(userGoogleEntity: UserGoogleEntity) {
        dao.createNewUserWithSignWithGoogle(userGoogleEntity)
    }

    override suspend fun updateMyObject(myObject: MyObject) = dao.updateMyObject(myObject)


    override suspend fun getAllGoogleUsers(): List<UserGoogleEntity> = dao.getAllGoogleUsers()


    override suspend fun deleteAllMyObjects() = dao.deleteAllMyObjects()

}