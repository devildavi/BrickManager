package com.example.brickmanager.presentation.inventory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brickmanager.domain.usecase.AddSetToInventoryUseCase
import com.brickmanager.domain.usecase.GetSetInventoryUseCase
import com.brickmanager.presentation.inventory.InventoryUiState
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
 * @param addSetToInventoryUseCase The use case for adding a new set to the inventory.
 */
@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val getSetInventoryUseCase: GetSetInventoryUseCase,
    private val addSetToInventoryUseCase: AddSetToInventoryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(InventoryUiState())
    val uiState: StateFlow<InventoryUiState> = _uiState.asStateFlow()

    init {
        loadInventory()
    }

    fun onAddSetClicked(setId: String) {
        viewModelScope.launch {
            try {
                addSetToInventoryUseCase(setId)
            } catch (e: IllegalArgumentException) {
                _uiState.update { it.copy(errorMessage = e.message) }
            }
        }
    }

    private fun loadInventory() {
        viewModelScope.launch {
            getSetInventoryUseCase()
                .onStart {
                    _uiState.update { it.copy(isLoading = true, errorMessage = null) }
                }
                .catch { exception ->
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = "Error: ${exception.message}")
                    }
                }
                .collect { sets ->
                    _uiState.update {
                        it.copy(isLoading = false, inventorySets = sets)
                    }
                }
        }
    }
}