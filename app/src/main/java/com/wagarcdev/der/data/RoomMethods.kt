package com.wagarcdev.der.data

import android.content.Context
import android.util.Log
import com.wagarcdev.compose_mvvm_empty_project.data.local.AppDatabase
import com.wagarcdev.der.data.entities.UserEntity
import com.wagarcdev.der.domain.model.Users
import com.wagarcdev.der.domain.repository.GoogleRepository
import com.wagarcdev.der.domain.repository.SimpleUserRepository

class RoomMethods(context: Context) : GoogleRepository, SimpleUserRepository {

    private val myDao = AppDatabase.getInstance(context).dao

    override fun createNewUserWithSignWithGoogle(users: Users) {
        myDao.createNewUserWithSignWithGoogle(UserEntity.fromModelToEntity(users))
    }

    override fun getAllGoogleUsers(): List<Users> {
        val listUserEntity = myDao.getAllGoogleUsers(false)
        return listUserEntity.map { it.fromEntityToModel() }
    }

    override fun createNewSimpleUser(user: Users) {
        myDao.createNewSimpleUser(UserEntity.fromModelToEntity(user))
        Log.i("TAG", user.toString() + "etapa2")
    }

    override fun getAllUsers(): List<Users> {
        return myDao.getAllSimpleUsers(true).map {
            it.fromEntityToModel()
        }
    }

    override fun validateLogin(isCommonUser: Boolean, username: String): String {
        return myDao.validateLogin(isCommonUser, username)
    }

    override fun getUserId(isCommonUser: Boolean, username: String): String {
        return myDao.getUserId(isCommonUser, username)
    }

    override fun getUserById(id: String): Users {
        return myDao.getUserById(id).fromEntityToModel()
    }

}