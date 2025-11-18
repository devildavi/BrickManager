package com.brickmanager.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.brickmanager.data.entity.SetEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for the `inventory_sets` table.
 * Provides methods to interact with the set data in the database.
 */
@Dao
interface SetDao {

    /**
     * Fetches all sets from the `inventory_sets` table as a reactive stream.
     * @return A [Flow] emitting a list of all [SetEntity] objects in the database.
     */
    @Query("SELECT * FROM inventory_sets")
    fun getSetInventory(): Flow<List<SetEntity>>

    /**
     * Inserts a set into the database. If the set already exists, it is replaced.
     * @param set The [SetEntity] to be inserted or updated.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSet(set: SetEntity)

    /**
     * Updates the built status of a specific set.
     * @param setId The ID of the set to update.
     * @param isBuilt The new built status to set.
     */
    @Query("UPDATE inventory_sets SET isBuilt = :isBuilt WHERE id = :setId")
    suspend fun updateSetBuiltStatus(setId: String, isBuilt: Boolean)
}
