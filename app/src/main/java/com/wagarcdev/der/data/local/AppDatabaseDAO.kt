package com.wagarcdev.der.data.local

import androidx.room.*
import com.wagarcdev.der.data.entities.ReportEntity
import com.wagarcdev.der.data.entities.ServiceEntity
import com.wagarcdev.der.data.entities.UserEntity

@Dao
interface AppDatabaseDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createNewUserWithSignWithGoogle(userEntity: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createNewSimpleUser(userEntity: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createNewReport(reportEntity: ReportEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createNewService(serviceEntity: ServiceEntity)

    @Query("SELECT * from Usuarios WHERE isCommonUser = :isCommonUser")
    suspend fun getAllGoogleUsers(isCommonUser: Boolean): List<UserEntity>

    @Query("SELECT * from Usuarios WHERE isCommonUser = :isCommonUser")
    suspend fun getAllSimpleUsers(isCommonUser: Boolean): List<UserEntity>

    @Query("SELECT password FROM Usuarios WHERE isCommonUser = :isCommonUser AND username = :username")
    suspend fun validateLogin(isCommonUser: Boolean, username: String): String

    @Query("SELECT id FROM Usuarios WHERE isCommonUser = :isCommonUser AND username = :username")
    suspend fun getUserId(isCommonUser: Boolean, username: String): String

    @Query("SELECT * FROM Usuarios WHERE id =:id")
    suspend fun getUserById(id: String): UserEntity
}