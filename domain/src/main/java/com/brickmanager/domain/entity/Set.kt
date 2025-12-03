package com.brickmanager.domain.entity

/**
 * Represents a single LEGO set in the domain layer.
 * This is a pure data class representing the business model.
 *
 * @property id The unique identifier for the set (e.g., "75301-1").
 * @property name The official name of the set.
 * @property series The theme or series the set belongs to (e.g., "Star Wars").
 * @property pieceCount The total number of pieces in the set.
 * @property minifigCount The number of minifigures included in the set.
 * @property isBuilt Flag indicating whether the user has built this set.
 * @property imageUrl The URL for an image of the set.
 * @property acquisitionDate The date when the set was acquired.
 * @property buildDate The date when the set was marked as built.
 */
data class Set(
    val id: String,
    val name: String,
    val series: String,
    val pieceCount: Int,
    val minifigCount: Int?,
    val isBuilt: Boolean = false,
    val imageUrl: String?,
    val acquisitionDate: String?,
    val buildDate: String?
)
