package com.brickmanager.data.remote

import com.brickmanager.data.remote.model.RebrickableSetResponse
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
}
