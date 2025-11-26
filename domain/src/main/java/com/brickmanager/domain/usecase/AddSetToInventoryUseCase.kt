package com.brickmanager.domain.usecase

import com.brickmanager.domain.repository.InventoryRepository

/**
 * A use case that encapsulates the business logic for adding a set to the user's inventory.
 * It depends on the [InventoryRepository] interface to perform the data operation.
 *
 * @param inventoryRepository The repository responsible for inventory data management.
 */
class AddSetToInventoryUseCase(private val inventoryRepository: InventoryRepository) {

    /**
     * Executes the use case.
     * @param setId The ID of the set to be added to the inventory.
     * @throws IllegalArgumentException if the setId is blank.
     */
    suspend operator fun invoke(setId: String) {
        if (setId.isBlank()) {
            throw IllegalArgumentException("Set ID cannot be empty or blank.")
        }
        inventoryRepository.addSet(setId)
    }
}