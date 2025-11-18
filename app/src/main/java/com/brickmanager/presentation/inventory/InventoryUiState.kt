package com.brickmanager.presentation.inventory

import com.brickmanager.domain.entity.Set

/**
 * Represents the state of the Inventory UI at any given time.
 * This class is observed by the Composable screen to render the appropriate UI.
 *
 * @property isLoading `true` if the inventory is currently being loaded, `false` otherwise.
 * @property inventorySets The list of [Set] objects to be displayed.
 * @property errorMessage A description of an error if one has occurred, otherwise `null`.
 */
data class InventoryUiState(
    val isLoading: Boolean = false,
    val inventorySets: List<Set> = emptyList(),
    val errorMessage: String? = null
)
