package com.brickmanager.data.remote

import com.brickmanager.data.remote.model.RebrickableMinifigResponse
import com.brickmanager.data.remote.model.RebrickableSetResponse
import com.brickmanager.data.remote.model.RebrickableThemeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Defines the API endpoints for interacting with the Rebrickable API.
 * This interface will be implemented by Retrofit.
 */
interface RebrickableApiService {

    /**
     * Fetches the details for a specific set by its number.
     * @param setNum The set number (e.g., "75301-1").
     * @param apiKey The API key for authentication.
     * @return A [RebrickableSetResponse] object containing the set's details.
     */
    @GET("lego/sets/{set_num}/")
    suspend fun getSetDetails(
        @Path("set_num") setNum: String,
        @Query("key") apiKey: String
    ): RebrickableSetResponse

    /**
     * Fetches the list of minifigures for a specific set.
     * @param setNum The set number (e.g., "75301-1").
     * @param apiKey The API key for authentication.
     * @return A [RebrickableMinifigResponse] object containing a list of minifigures.
     */
    @GET("lego/sets/{set_num}/minifigs/")
    suspend fun getMinifiguresForSet(
        @Path("set_num") setNum: String,
        @Query("key") apiKey: String
    ): RebrickableMinifigResponse

    /**
     * Fetches the details for a specific theme by its ID.
     * @param themeId The ID of the theme.
     * @param apiKey The API key for authentication.
     * @return A [RebrickableThemeResponse] object containing the theme's details.
     */
    @GET("lego/themes/{theme_id}/")
    suspend fun getThemeDetails(
        @Path("theme_id") themeId: Int,
        @Query("key") apiKey: String
    ): RebrickableThemeResponse
}