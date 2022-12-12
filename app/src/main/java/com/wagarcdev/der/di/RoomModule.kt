package com.wagarcdev.der.di

import android.content.Context
import androidx.room.Room
import com.wagarcdev.der.data.datasource.local.AppDatabase
import com.wagarcdev.der.data.datasource.local.dao.ReportsDao
import com.wagarcdev.der.data.datasource.local.dao.UsersDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for Room Database.
 */
@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    @Singleton
    fun providesAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(
        context, AppDatabase::class.java, "der_database"
    ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun providesUsersDao(
        appDatabase: AppDatabase
    ): UsersDao = appDatabase.usersDao

    @Provides
    @Singleton
    fun providesReportsDao(
        appDatabase: AppDatabase
    ): ReportsDao = appDatabase.reportsDao
}