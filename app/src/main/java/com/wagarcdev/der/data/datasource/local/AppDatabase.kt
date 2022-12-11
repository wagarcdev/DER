package com.wagarcdev.der.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wagarcdev.der.data.datasource.local.dao.ReportsDao
import com.wagarcdev.der.data.datasource.local.dao.UsersDao
import com.wagarcdev.der.data.datasource.local.entity.ReportEntity
import com.wagarcdev.der.data.datasource.local.entity.ServiceEntity
import com.wagarcdev.der.data.datasource.local.entity.UserEntity

@Database(
    entities = [UserEntity::class, ReportEntity::class, ServiceEntity::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(DatabaseConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val usersDao: UsersDao
    abstract val reportsDao: ReportsDao
}