package com.brickmanager.domain.entity

/**
 * Represents a single LEGO set in the domain layer.
 * This is a pure data class representing the business model.
 *
 * @property id The unique identifier for the set (e.g., "75301").
 * @property name The official name of the set.
 * @property series The theme or series the set belongs to (e.g., "Star Wars").
 * @property pieceCount The total number of pieces in the set.
 * @property isBuilt Flag indicating whether the user has built this set.
 * @property estimatedMarketValue The approximate market value, used for statistics.
 */
data class Set(
    val id: String,
    val name: String,
    val series: String,
    val pieceCount: Int,
    val isBuilt: Boolean = false,
    val estimatedMarketValue: Double
)
