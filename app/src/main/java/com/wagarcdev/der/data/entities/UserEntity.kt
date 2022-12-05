package com.wagarcdev.der.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wagarcdev.der.domain.model.User

@Entity(tableName = "Usuarios")
class UserEntity(
    @PrimaryKey(autoGenerate = false)
    var id: String = "0",
    var email: String? = "email@teste",
    var username: String? = "username",
    var fullname: String? = "fullname",
    var displayName: String? = "displayname",
    var photoUrl: String? = "photo",
    var password: String? = "password",
    var isCommonUser: Boolean = true
) {
    companion object {
        fun fromModelToEntity(simpleUser: User) = UserEntity(
            simpleUser.id,
            simpleUser.email,
            simpleUser.username,
            simpleUser.fullname,
            simpleUser.displayName,
            simpleUser.photoUrl,
            simpleUser.password,
            simpleUser.isCommonUser
        )
    }

    fun fromEntityToModel() =
        User(id, email, username, fullname, displayName, photoUrl, password, isCommonUser)
}