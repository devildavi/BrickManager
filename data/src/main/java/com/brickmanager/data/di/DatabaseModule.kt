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

/**
 * Hilt module that provides database-related dependencies.
 * It is installed in the [SingletonComponent], meaning the provided instances
 * will have a singleton scope throughout the application's lifecycle.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /**
     * Provides a singleton instance of the [AppDatabase].
     * @param context The application context, provided by Hilt.
     * @return A singleton [AppDatabase] instance.
     */
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "brickmanager-db"
        ).build()
    }

    /**
     * Provides an instance of the [SetDao].
     * This function depends on the [AppDatabase] instance provided by [provideAppDatabase].
     * @param database The singleton [AppDatabase] instance.
     * @return An instance of [SetDao].
     */
    @Provides
    fun provideSetDao(database: AppDatabase): SetDao {
        return database.setDao()
    }
}
