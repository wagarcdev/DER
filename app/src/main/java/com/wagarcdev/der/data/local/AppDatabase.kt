package com.wagarcdev.der.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wagarcdev.der.data.entities.ReportEntity
import com.wagarcdev.der.data.entities.ServiceEntity
import com.wagarcdev.der.data.entities.UserEntity

@Database(
    entities = [UserEntity::class, ReportEntity::class, ServiceEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(DatabaseConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val dao: AppDatabaseDAO
}