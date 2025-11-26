package com.brickmanager.data.repository

import com.brickmanager.data.BuildConfig
import com.brickmanager.data.dao.SetDao
import com.brickmanager.data.entity.SetEntity
import com.brickmanager.data.mapper.toDomain
import com.brickmanager.data.remote.RebrickableApiService
import com.brickmanager.domain.entity.Set
import com.brickmanager.domain.repository.InventoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

/**
 * Concrete implementation of the [InventoryRepository] interface.
 * This class is responsible for orchestrating data operations between the local database
 * and the Rebrickable remote API.
 *
 * @param setDao The Data Access Object for inventory sets, provided by Room.
 * @param apiService The Retrofit service for fetching remote set details from Rebrickable.
 */
class InventoryRepositoryImpl @Inject constructor(
    private val setDao: SetDao,
    private val apiService: RebrickableApiService
) : InventoryRepository {

    override fun getSetInventory(): Flow<List<Set>> {
        return setDao.getSetInventory().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun addSet(setId: String) {
        try {
            val formattedSetId = if (setId.contains("-")) setId else "$setId-1"

            val remoteDetails = apiService.getSetDetails(formattedSetId, BuildConfig.REBRICKABLE_API_KEY)

            val newSetEntity = SetEntity(
                id = remoteDetails.set_num,
                name = remoteDetails.name,
                pieceCount = remoteDetails.num_parts,
                isBuilt = false,
                imageUrl = remoteDetails.set_img_url
            )
            setDao.insertSet(newSetEntity)

        } catch (e: IOException) {
            throw IOException("Network error connecting to Rebrickable. Please try again later.")
        } catch (e: retrofit2.HttpException) {
            throw Exception("The set with ID $setId does not exist or the Rebrickable server failed (${e.code()}) - ${e.message()}")
        } catch (e: Exception) {
            throw Exception("An unknown error occurred while processing set information.")
        }
    }

    override suspend fun updateSetBuiltStatus(setId: String, isBuilt: Boolean) {
        setDao.updateSetBuiltStatus(setId, isBuilt)
    }

    override suspend fun getMissingBricksForWishlist(): List<com.brickmanager.domain.entity.Brick> {
        TODO("Not yet implemented")
    }
}