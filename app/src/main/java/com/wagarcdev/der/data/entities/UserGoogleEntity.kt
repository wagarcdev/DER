package com.wagarcdev.der.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wagarcdev.der.domain.model.UserGoogle

@Entity(tableName = "UsuarioGoogle")
class UserGoogleEntity(
    @PrimaryKey
    @ColumnInfo(name = "userId")
    val userId: String,
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "displayName")
    val displayName: String,
    @ColumnInfo(name = "photoUrl")
    val photoUrl: String
) {

    companion object {
        fun fromModelToEntity(userGoogle: UserGoogle) = UserGoogleEntity(
            userGoogle.id,
            userGoogle.email,
            userGoogle.displayName,
            userGoogle.photoUrl
        )
    }

    fun getAll() = UserGoogle(userId, email, displayName, photoUrl)

}