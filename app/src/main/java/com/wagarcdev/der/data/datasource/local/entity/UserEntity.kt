package com.wagarcdev.der.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wagarcdev.der.domain.model.User

/**
 * Room entity for [User].
 */
@Entity(tableName = "users")
class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val email: String,
    val password: String
)