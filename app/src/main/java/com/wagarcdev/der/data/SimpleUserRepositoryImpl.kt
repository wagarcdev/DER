package com.wagarcdev.der.data

import com.wagarcdev.der.data.entities.UserEntity
import com.wagarcdev.der.data.local.AppDatabaseDAO
import com.wagarcdev.der.domain.model.User
import com.wagarcdev.der.domain.repository.SimpleUserRepository
import javax.inject.Inject

// TODO(GIVE A BETTER NAME)
class SimpleUserRepositoryImpl @Inject constructor(
    private val dao: AppDatabaseDAO
) : SimpleUserRepository {
    override suspend  fun createNewSimpleUser(user: User) {
        dao.createNewSimpleUser(UserEntity.fromModelToEntity(user))
    }

    override suspend  fun getAllUsers(): List<User> {
        return dao.getAllSimpleUsers(true).map {
            it.fromEntityToModel()
        }
    }

    override suspend  fun validateLogin(isCommonUser: Boolean, username: String): String {
        return dao.validateLogin(isCommonUser, username)
    }

    override suspend  fun getUserId(isCommonUser: Boolean, username: String): String {
        return dao.getUserId(isCommonUser, username)
    }

    override suspend  fun getUserById(id: String): User {
        return dao.getUserById(id).fromEntityToModel()
    }
}