package com.brickmanager.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a single LEGO set in the database.
 * This entity is used by Room to create the `inventory_sets` table.
 *
 * @property id The unique identifier for the set, serving as the Primary Key.
 * @property name The official name of the set.
 * @property series The theme or series the set belongs to (e.g., "Star Wars").
 * @property pieceCount The total number of pieces in the set.
 * @property minifigCount The number of minifigures included in the set.
 * @property isBuilt Flag indicating whether the user has built this set.
 * @property imageUrl The URL for an image of the set.
 * @property acquisitionDate The date when the set was acquired.
 * @property buildDate The date when the set was marked as built.
 */
@Entity(tableName = "inventory_sets")
data class SetEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val series: String,
    val pieceCount: Int,
    val minifigCount: Int?,
    val isBuilt: Boolean,
    val imageUrl: String?,
    val acquisitionDate: String?,
    val buildDate: String?
)
