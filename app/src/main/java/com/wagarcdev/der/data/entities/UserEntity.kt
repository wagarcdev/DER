package com.wagarcdev.der.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wagarcdev.der.domain.model.User

@Entity(tableName = "Usuarios")
class UserEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val email: String?,
    val username: String?,
    val fullname: String?,
    val displayName: String?,
    val photoUrl: String?,
    val password: String?,
    val isCommonUser: Boolean
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