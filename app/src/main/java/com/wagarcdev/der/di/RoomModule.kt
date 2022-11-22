package com.wagarcdev.der.di

import android.content.Context
import androidx.room.Room
import com.wagarcdev.der.data.local.AppDatabase
import com.wagarcdev.der.data.local.AppDatabaseDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(
        context, AppDatabase::class.java, "der_database"
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideAppDAO(
        appDatabase: AppDatabase
    ): AppDatabaseDAO = appDatabase.dao
}