package com.wagarcdev.der.data

import com.wagarcdev.der.domain.model.MyObject
import com.wagarcdev.der.data.entities.SimpleUserEntity
import com.wagarcdev.der.data.entities.UserGoogleEntity
import com.wagarcdev.der.data.local.AppDatabaseDAO
import com.wagarcdev.der.domain.model.SimpleUser
import com.wagarcdev.der.domain.model.UserGoogle
import com.wagarcdev.der.domain.repository.AppRepository
import com.wagarcdev.der.domain.repository.GoogleRepository
import com.wagarcdev.der.domain.repository.SimpleUserRepository

class AppRepositoryImplementation(
    private val dao: AppDatabaseDAO
) : AppRepository, GoogleRepository, SimpleUserRepository {

    override suspend fun getMyObjectById(myObjectId: Long): MyObject {
        return dao.getMyObjectById(myObjectId)
    }

    override suspend fun updateMyObject(myObject: MyObject) = dao.updateMyObject(myObject)

    override suspend fun deleteAllMyObjects() = dao.deleteAllMyObjects()

    override fun createNewUserWithSignWithGoogle(userGoogle: UserGoogle) {
        dao.createNewUserWithSignWithGoogle(UserGoogleEntity.fromModelToEntity(userGoogle))
    }

    override fun getAllGoogleUsers(): List<UserGoogle> {
        return dao.getAllGoogleUsers().map {
            it.getAll()
        }
    }

    override fun createNewSimpleUser(simpleUser: SimpleUser) {
        dao.createNewSimpleUser(SimpleUserEntity.fromModelToEntity(simpleUser))
    }

    override fun getAllUsers(): List<SimpleUser> {
        return dao.getAllSimpleUsers().map {
            it.getAllSimpleUser()
        }
    }

}