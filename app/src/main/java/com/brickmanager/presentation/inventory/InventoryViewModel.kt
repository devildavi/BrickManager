package com.brickmanager.presentation.inventory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brickmanager.domain.usecase.GetSetInventoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Inventory screen.
 * It is responsible for fetching inventory data, managing the UI state, and handling user events.
 *
 * @param getSetInventoryUseCase The use case for retrieving the user's set inventory.
 */
@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val getSetInventoryUseCase: GetSetInventoryUseCase
    // private val addSetToInventoryUseCase: AddSetToInventoryUseCase // To be used later
) : ViewModel() {

    private val _uiState = MutableStateFlow(InventoryUiState())
    /**
     * The public, read-only [StateFlow] of the inventory screen's UI state.
     * The UI layer collects this flow to react to state changes.
     */
    val uiState: StateFlow<InventoryUiState> = _uiState.asStateFlow()

    init {
        loadInventory()
    }

    /**
     * Initiates the loading of the user's inventory.
     * It launches a coroutine that collects data from the use case and updates the UI state accordingly.
     * It handles loading, success, and error states.
     */
    private fun loadInventory() {
        viewModelScope.launch {
            getSetInventoryUseCase()
                .onStart {
                    // Emit loading state
                    _uiState.update { it.copy(isLoading = true, errorMessage = null) }
                }
                .catch { exception ->
                    // Emit error state
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = "Error: ${exception.message}")
                    }
                }
                .collect { sets ->
                    // Emit success state with data
                    _uiState.update {
                        it.copy(isLoading = false, inventorySets = sets)
                    }
                }
        }
    }
}
