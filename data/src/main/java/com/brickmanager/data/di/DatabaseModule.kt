package com.brickmanager.data.di

import android.content.Context
import androidx.room.Room
import com.brickmanager.data.dao.SetDao
import com.brickmanager.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "brickmanager-db"
        )
        .fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    fun provideSetDao(database: AppDatabase): SetDao {
        return database.setDao()
    }
}