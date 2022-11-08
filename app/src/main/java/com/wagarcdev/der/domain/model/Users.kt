package com.wagarcdev.der.domain.model

data class Users (
    val id: String,
    val email: String?,
    val username : String?,
    val fullname:String?,
    val displayName: String?,
    val photoUrl: String?,
    val password:String?,
    val isCommonUser:Boolean
    )