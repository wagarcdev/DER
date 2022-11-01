package com.wagarcdev.der.data.local

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.wagarcdev.compose_mvvm_empty_project.domain.model.MyObject
import com.wagarcdev.der.data.entities.UserGoogleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDatabaseDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createNewUserWithSignWithGoogle(userGoogleEntity: UserGoogleEntity)

    @Update
    fun updateMyObject(myObject: MyObject)

    @Query("SELECT * from UsuarioGoogle")
    fun getAllGoogleUsers(): List<UserGoogleEntity>

    @Query("SELECT * from my_object_tbl where id=:id")
    fun getMyObjectById(id: Long): MyObject

    @Query("DELETE  from my_object_tbl where id=:id")
    fun deleteMyObjectById(id: Long)

    @Query("DELETE from my_object_tbl")
    fun deleteAllMyObjects()

}