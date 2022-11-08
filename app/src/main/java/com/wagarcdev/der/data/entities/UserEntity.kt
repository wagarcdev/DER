package com.wagarcdev.der.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wagarcdev.der.domain.model.Users

@Entity(tableName = "Usuarios")
class UserEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val email: String?,
    val username : String?,
    val fullname:String?,
    val displayName: String?,
    val photoUrl: String?,
    val password:String?,
    val isCommonUser:Boolean
) {
    companion object {
        fun fromModelToEntity(simpleUser: Users) = UserEntity(
            simpleUser.id,
            simpleUser.username,
            simpleUser.fullname,
            simpleUser.email,
            simpleUser.displayName,
            simpleUser.photoUrl,
            simpleUser.password,
            simpleUser.isCommonUser
        )
    }

    fun getAllSimpleUser() = Users(id, email, username, fullname, displayName, photoUrl, password, isCommonUser)
}