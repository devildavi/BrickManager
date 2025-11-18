package com.brickmanager.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.brickmanager.data.dao.SetDao
import com.brickmanager.data.entity.SetEntity

/**
 * The main Room database class for the application.
 * It lists the entities and provides access to the DAOs.
 *
 * @see SetEntity
 * @see SetDao
 */
@Database(entities = [SetEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Provides access to the [SetDao] for interacting with inventory set data.
     * @return An instance of [SetDao].
     */
    abstract fun setDao(): SetDao
}
