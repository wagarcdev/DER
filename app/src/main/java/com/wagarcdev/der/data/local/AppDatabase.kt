package com.wagarcdev.compose_mvvm_empty_project.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.wagarcdev.der.data.entities.ReportEntity
import com.wagarcdev.der.data.entities.ServiceEntity
import com.wagarcdev.der.domain.model.MyObject
import com.wagarcdev.der.data.entities.UserEntity
import com.wagarcdev.der.data.local.AppDatabaseDAO

@Database(
    entities = [MyObject::class, UserEntity::class, ReportEntity::class, ServiceEntity::class],
    version = 6
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        private const val DATABASE_NAME = "DER_DATABASE"
        private var INSTANCE: AppDatabase? = null

        private fun createDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration().allowMainThreadQueries().build()
        }

        fun getInstance(context: Context): AppDatabase {
            return (INSTANCE ?: createDatabase(context).also {
                INSTANCE = it
            })
        }
    }

    abstract val dao: AppDatabaseDAO

}