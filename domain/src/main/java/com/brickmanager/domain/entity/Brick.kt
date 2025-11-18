package com.brickmanager.domain.entity

/**
 * Represents a single type of LEGO brick in the domain layer.
 *
 * @property elementId The unique identifier for the brick part, often used by platforms like BrickLink.
 * @property colorId The identifier for the brick's color.
 * @property quantity The number of units of this brick.
 * @property isOwned Flag indicating whether the user owns this specific brick.
 */
data class Brick(
    val elementId: String,
    val colorId: Int,
    val quantity: Int,
    val isOwned: Boolean
)
