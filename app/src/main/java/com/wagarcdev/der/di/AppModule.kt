package com.wagarcdev.der.di

import android.app.Application
import androidx.room.Room
import com.wagarcdev.compose_mvvm_empty_project.data.local.AppDatabase
import com.wagarcdev.der.data.local.AppDatabaseDAO
import com.wagarcdev.der.data.remote.ApplicationAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "app_database.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideAppDAO(appDatabase: AppDatabase): AppDatabaseDAO = appDatabase.dao

    @Singleton
    @Provides
    fun provideApi(): ApplicationAPI {

        return Retrofit.Builder()
            .build()
            .create(ApplicationAPI::class.java)
    }
}