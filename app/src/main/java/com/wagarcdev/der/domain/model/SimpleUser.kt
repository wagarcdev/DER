package com.wagarcdev.der.domain.model

data class SimpleUser(
    val id : Int,
    val username : String,
    val fullname:String,
    val email:String,
    val password:String,
    val isCommonUser:Boolean = true
)