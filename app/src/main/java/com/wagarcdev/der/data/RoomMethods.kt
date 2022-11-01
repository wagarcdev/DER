package com.wagarcdev.der.data

import android.content.Context
import android.util.Log
import com.wagarcdev.compose_mvvm_empty_project.data.local.AppDatabase
import com.wagarcdev.der.data.entities.UserGoogleEntity
import com.wagarcdev.der.domain.model.UserGoogle
import com.wagarcdev.der.domain.repository.RoomDataSource

class RoomMethods(context: Context) : RoomDataSource {

    private val userGoogleDao = AppDatabase.getInstance(context).dao

    override fun createNewUserWithSignWithGoogle(userGoogle: UserGoogle) {
        userGoogleDao.createNewUserWithSignWithGoogle(UserGoogleEntity.fromModelToEntity(userGoogle))
        Log.i("TAG", "Usuário criado: $userGoogle")
    }

    override fun getAllGoogleUsers(): List<UserGoogle> {
        return userGoogleDao.getAllGoogleUsers().map {
            it.getAll()
        }
    }
}