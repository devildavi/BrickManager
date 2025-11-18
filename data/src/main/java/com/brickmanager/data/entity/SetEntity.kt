package com.brickmanager.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a single LEGO set in the database.
 * This entity is used by Room to create the `inventory_sets` table.
 *
 * @property id The unique identifier for the set, serving as the Primary Key.
 * @property name The official name of the set.
 * @property pieceCount The total number of pieces in the set.
 * @property isBuilt Flag indicating whether the user has built this set.
 * @property estimatedMarketValue The approximate market value of the set.
 */
@Entity(tableName = "inventory_sets")
data class SetEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val pieceCount: Int,
    val isBuilt: Boolean,
    val estimatedMarketValue: Double
)
