package com.brickmanager.data.remote.model

/**
 * Represents the response for a single set from the Rebrickable API.
 */
data class RebrickableSetResponse(
    val set_num: String,
    val name: String,
    val num_parts: Int,
    val year: Int,
    val set_img_url: String?,
    val theme_id: Int, // Added theme_id
    val last_modified_dt: String
)
