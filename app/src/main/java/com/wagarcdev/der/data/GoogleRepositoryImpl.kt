package com.wagarcdev.der.data

import com.wagarcdev.der.data.entities.UserEntity
import com.wagarcdev.der.data.local.AppDatabaseDAO
import com.wagarcdev.der.domain.model.User
import com.wagarcdev.der.domain.repository.GoogleRepository
import javax.inject.Inject

// TODO(GIVE A BETTER NAME)
class GoogleRepositoryImpl @Inject constructor(
    private val dao: AppDatabaseDAO
) : GoogleRepository {

    override suspend  fun createNewUserWithSignWithGoogle(users: User) {
        dao.createNewUserWithSignWithGoogle(UserEntity.fromModelToEntity(users))
    }

    override suspend  fun getAllGoogleUsers(): List<User> {
        val listUserEntity = dao.getAllGoogleUsers(false)
        return listUserEntity.map { it.fromEntityToModel() }
    }
}