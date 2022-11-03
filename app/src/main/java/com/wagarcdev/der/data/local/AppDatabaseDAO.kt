package com.wagarcdev.der.data.local

import androidx.room.*
import com.wagarcdev.compose_mvvm_empty_project.domain.model.MyObject
import com.wagarcdev.der.data.entities.SimpleUserEntity
import com.wagarcdev.der.data.entities.UserGoogleEntity

@Dao
interface AppDatabaseDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createNewUserWithSignWithGoogle(userGoogleEntity: UserGoogleEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createNewSimpleUser(userGoogleEntity: SimpleUserEntity)

    @Update
    fun updateMyObject(myObject: MyObject)

    @Query("SELECT * from UsuarioGoogle")
    fun getAllGoogleUsers(): List<UserGoogleEntity>

    @Query("SELECT * from UsuarioComum")
    fun getAllSimpleUsers(): List<SimpleUserEntity>


    @Query("SELECT * from my_object_tbl where id=:id")
    fun getMyObjectById(id: Long): MyObject

    @Query("DELETE  from my_object_tbl where id=:id")
    fun deleteMyObjectById(id: Long)

    @Query("DELETE from my_object_tbl")
    fun deleteAllMyObjects()

}