package com.brickmanager.data.remote.model

import com.google.gson.annotations.SerializedName

/**
 * Represents the top-level response from the Rebrickable API when fetching a list of minifigures.
 */
data class RebrickableMinifigResponse(
    val count: Int,
    val results: List<MinifigResult>
)

/**
 * Represents a single minifigure entry within the list response.
 */
data class MinifigResult(
    val id: Int,
    @SerializedName("set_num") val figNum: String,
    @SerializedName("set_name") val name: String,
    val quantity: Int,
    @SerializedName("set_img_url") val imageUrl: String?
)
