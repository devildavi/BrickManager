package com.brickmanager.domain.usecase

import com.brickmanager.domain.entity.Set
import com.brickmanager.domain.repository.InventoryRepository
import kotlinx.coroutines.flow.Flow

/**
 * A use case that retrieves the user's entire set inventory.
 * It depends on the [InventoryRepository] to fetch the data.
 *
 * @param inventoryRepository The repository responsible for inventory data.
 */
class GetSetInventoryUseCase(private val inventoryRepository: InventoryRepository) {

    /**
     * Executes the use case.
     * @return A [Flow] that emits the list of [Set] objects in the inventory.
     */
    operator fun invoke(): Flow<List<Set>> {
        return inventoryRepository.getSetInventory()
    }
}
