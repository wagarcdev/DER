package com.wagarcdev.der.data

import com.wagarcdev.der.domain.model.MyObject
import com.wagarcdev.der.data.entities.UserEntity
import com.wagarcdev.der.data.local.AppDatabaseDAO
import com.wagarcdev.der.domain.model.Users
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

    override fun createNewUserWithSignWithGoogle(users: Users) {
        dao.createNewUserWithSignWithGoogle(UserEntity.fromModelToEntity(users))
    }

    override fun getAllGoogleUsers(): List<Users> {
        val listUserEntity = dao.getAllGoogleUsers(false)
        return listUserEntity.map { it.fromEntityToModel() }
    }

    override fun createNewSimpleUser(simpleUser: Users) {
        dao.createNewSimpleUser(UserEntity.fromModelToEntity(simpleUser))
    }

    override fun getAllUsers(): List<Users> {
        return dao.getAllSimpleUsers(true).map {
            it.fromEntityToModel()
        }
    }

    override fun validateLogin(isCommonUser: Boolean, username: String): String {
        return dao.validateLogin(isCommonUser, username)
    }

    override fun getUserId(isCommonUser: Boolean, username: String): String {
        return dao.getUserId(isCommonUser, username)
    }

}