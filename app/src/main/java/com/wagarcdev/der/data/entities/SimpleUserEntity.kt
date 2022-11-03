package com.wagarcdev.der.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wagarcdev.der.domain.model.SimpleUser

@Entity(tableName = "UsuarioComum")
class SimpleUserEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "username")
    val username: String,
    @ColumnInfo(name = "fullname")
    val fullname: String,
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "password")
    val password: String,
    @ColumnInfo(name = "common_user")
    val isCommonUser: Boolean = true
) {
    companion object {
        fun fromModelToEntity(simpleUser: SimpleUser) = SimpleUserEntity(
            simpleUser.id,
            simpleUser.username,
            simpleUser.fullname,
            simpleUser.email,
            simpleUser.password,
            simpleUser.isCommonUser
        )
    }

    fun getAllSimpleUser() = SimpleUser(id, username, fullname, email, password, isCommonUser)
}