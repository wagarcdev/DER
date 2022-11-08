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

    override fun validateLogin(isCommonUser: Boolean, email: String, password: String): String {
        var id = myDao.validateLogin(isCommonUser, email)
        Log.i("TAG", id + "room")
        return id
    }

}