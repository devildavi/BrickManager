package com.brickmanager.domain.repository

import com.brickmanager.domain.entity.OfferStatus
import com.brickmanager.domain.entity.TradeOffer
import kotlinx.coroutines.flow.Flow

/**
 * Defines the contract for managing trade offers.
 * This interface is part of the domain layer and is implemented in the data layer.
 */
interface TradeRepository {

    /**
     * Creates a new trade offer in the system.
     * @param offer The [TradeOffer] object to be created.
     */
    suspend fun createOffer(offer: TradeOffer)

    /**
     * Finds trade offers based on a search query and an optional distance.
     * @param query The search query, which could be a set name, series, etc.
     * @param maxDistanceKm The maximum distance in kilometers to search for offers. Can be null.
     * @return A [Flow] emitting a list of matching [TradeOffer] objects.
     */
    fun findOffers(query: String, maxDistanceKm: Int?): Flow<List<TradeOffer>>

    /**
     * Updates the status of an existing trade offer.
     * @param offerId The ID of the offer to update.
     * @param status The new [OfferStatus] to set (e.g., ACCEPTED, CANCELLED).
     */
    suspend fun updateOfferStatus(offerId: String, status: OfferStatus)
}
