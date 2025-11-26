package com.brickmanager.domain.entity

/**
 * Represents a trade offer made by a user for a LEGO set.
 *
 * @property offerId The unique identifier for this trade offer.
 * @property creatorUserId The ID of the user who created the offer.
 * @property exchangeSetId The ID of the set the creator is offering.
 * @property desiredSetId The ID of the set the creator wishes to receive.
 * @property status The current status of the trade offer.
 */
data class TradeOffer(
    val offerId: String,
    val creatorUserId: String,
    val exchangeSetId: String,
    val desiredSetId: String,
    val status: OfferStatus
)
