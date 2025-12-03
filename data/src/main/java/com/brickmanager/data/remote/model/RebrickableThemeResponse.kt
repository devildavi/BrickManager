package com.brickmanager.data.remote.model

/**
 * Represents the response from the Rebrickable API when fetching theme details.
 */
data class RebrickableThemeResponse(
    val id: Int,
    val name: String,
    val parent_id: Int?
)
