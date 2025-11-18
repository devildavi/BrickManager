package com.brickmanager.data.mapper

import com.brickmanager.data.entity.SetEntity
import com.brickmanager.domain.entity.Set

/**
 * Converts a data-layer [SetEntity] to a domain-layer [Set] object.
 * This is used to map database models to business models.
 * @return The corresponding [Set] object.
 */
fun SetEntity.toDomain(): Set {
    return Set(
        id = this.id,
        name = this.name,
        series = "TODO", // Not stored in the local DB, simplified for now.
        pieceCount = this.pieceCount,
        isBuilt = this.isBuilt,
        estimatedMarketValue = this.estimatedMarketValue
    )
}

/**
 * Converts a domain-layer [Set] to a data-layer [SetEntity] object.
 * This is used to map business models to database entities for insertion.
 * @return The corresponding [SetEntity] object.
 */
fun Set.toEntity(): SetEntity {
    return SetEntity(
        id = this.id,
        name = this.name,
        pieceCount = this.pieceCount,
        isBuilt = this.isBuilt,
        estimatedMarketValue = this.estimatedMarketValue
    )
}
