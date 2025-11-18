package com.brickmanager.domain.repository

import com.brickmanager.domain.entity.Brick
import com.brickmanager.domain.entity.Set
import kotlinx.coroutines.flow.Flow

/**
 * Defines the contract for managing the user's set inventory.
 * This interface is part of the domain layer and is implemented in the data layer.
 */
interface InventoryRepository {

    /**
     * Retrieves the user's entire inventory of sets as a reactive stream.
     * @return A [Flow] emitting a list of [Set] objects.
     */
    fun getSetInventory(): Flow<List<Set>>

    /**
     * Adds a new set to the user's collection.
     * The implementation will typically fetch set details from a remote source
     * and then store them locally.
     * @param setId The unique ID of the set to add.
     */
    suspend fun addSet(setId: String)

    /**
     * Updates the built status of a specific set in the inventory.
     * @param setId The ID of the set to update.
     * @param isBuilt The new built status (`true` if built, `false` otherwise).
     */
    suspend fun updateSetBuiltStatus(setId: String, isBuilt: Boolean)

    /**
     * Calculates and retrieves a list of missing bricks required for a wishlist.
     * @return A list of [Brick] objects that are missing.
     */
    suspend fun getMissingBricksForWishlist(): List<Brick>
}
