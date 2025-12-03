package com.brickmanager.domain.usecase

import com.brickmanager.domain.repository.InventoryRepository

/**
 * Use case responsible for updating the status of a LEGO Set
 * (e.g., 'Owned', 'Wanted', 'Sold').
 */
class UpdateSetStatusUseCase(
    private val inventoryRepository: InventoryRepository
) {
    /**
     * @param setNumber The set number (e.g., "75301").
     * @param newStatus The new status for the set.
     */
    suspend operator fun invoke(setNumber: String, newStatus: Boolean) {
        // Optional business logic here (e.g., validation)
        if (setNumber.isBlank()) {
            throw IllegalArgumentException("Set number cannot be empty.")
        }

        // Call the repository method to execute the update
        inventoryRepository.updateSetBuiltStatus(setNumber, newStatus)
    }
}