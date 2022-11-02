package com.wagarcdev.der.data

import android.content.Context
import com.wagarcdev.compose_mvvm_empty_project.data.local.AppDatabase
import com.wagarcdev.der.data.entities.UserGoogleEntity
import com.wagarcdev.der.domain.model.UserGoogle
import com.wagarcdev.der.domain.repository.GoogleRepository

class RoomMethods(context: Context) : GoogleRepository {

    private val userGoogleDao = AppDatabase.getInstance(context).dao

    override fun createNewUserWithSignWithGoogle(userGoogle: UserGoogle) {
        userGoogleDao.createNewUserWithSignWithGoogle(UserGoogleEntity.fromModelToEntity(userGoogle))
    }

    override fun getAllGoogleUsers(): List<UserGoogle> {
        return userGoogleDao.getAllGoogleUsers().map {
            it.getAll()
        }
    }
}