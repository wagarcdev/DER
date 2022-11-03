package com.wagarcdev.der.data

import android.content.Context
import android.util.Log
import com.wagarcdev.compose_mvvm_empty_project.data.local.AppDatabase
import com.wagarcdev.der.data.entities.SimpleUserEntity
import com.wagarcdev.der.data.entities.UserGoogleEntity
import com.wagarcdev.der.domain.model.SimpleUser
import com.wagarcdev.der.domain.model.UserGoogle
import com.wagarcdev.der.domain.repository.GoogleRepository
import com.wagarcdev.der.domain.repository.SimpleUserRepository

class RoomMethods(context: Context) : GoogleRepository, SimpleUserRepository {

    private val myDao = AppDatabase.getInstance(context).dao

    override fun createNewUserWithSignWithGoogle(userGoogle: UserGoogle) {
        myDao.createNewUserWithSignWithGoogle(UserGoogleEntity.fromModelToEntity(userGoogle))
    }

    override fun getAllGoogleUsers(): List<UserGoogle> {
        return myDao.getAllGoogleUsers().map {
            it.getAll()
        }
    }

    override fun createNewSimpleUser(simpleUser: SimpleUser) {
        myDao.createNewSimpleUser(SimpleUserEntity.fromModelToEntity(simpleUser))
        Log.i("TAG", simpleUser.toString() + "etapa2")
    }

    override fun getAllUsers(): List<SimpleUser> {
        return myDao.getAllSimpleUsers().map {
            it.getAllSimpleUser()
        }
    }

}