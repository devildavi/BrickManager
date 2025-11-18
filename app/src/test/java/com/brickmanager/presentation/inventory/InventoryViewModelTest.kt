package com.brickmanager.presentation.inventory

import app.cash.turbine.test
import com.brickmanager.domain.entity.Set
import com.brickmanager.domain.usecase.GetSetInventoryUseCase
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class InventoryViewModelTest {

    @MockK
    private lateinit var mockGetSetInventoryUseCase: GetSetInventoryUseCase

    private lateinit var viewModel: InventoryViewModel

    // 1. Usamos StandardTestDispatcher para controlar la ejecución
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)

        val fakeSets = listOf(Set(id = "10294", name = "Titanic", series = "Creator", pieceCount = 9090, estimatedMarketValue = 679.99))
        every { mockGetSetInventoryUseCase.invoke() } returns flowOf(fakeSets)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadInventory should transition through loading to success state`() = runTest {
        // ARRANGE: Inicializamos el ViewModel. Su bloque init{} agenda la corrutina, pero NO la ejecuta aún.
        viewModel = InventoryViewModel(mockGetSetInventoryUseCase)

        viewModel.uiState.test {
            // 1. AWAIT INICIAL: El estado por defecto antes de que la corrutina se ejecute.
            assertEquals(InventoryUiState(isLoading = false), awaitItem())

            // 2. AVANZAR EL DISPATCHER: Ejecutamos las corrutinas pendientes.
            // Esto correrá el onStart {} y el collect {} del ViewModel.
            testDispatcher.scheduler.advanceUntilIdle()

            // 3. AWAIT LOADING: Verificamos el estado de carga emitido por onStart.
            val loadingState = awaitItem()
            assertEquals(true, loadingState.isLoading)
            assertEquals(0, loadingState.inventorySets.size)

            // 4. AWAIT SUCCESS: Verificamos el estado final con los datos.
            val successState = awaitItem()
            assertEquals(false, successState.isLoading)
            assertEquals(1, successState.inventorySets.size)
            assertEquals("Titanic", successState.inventorySets.first().name)

            // 5. Verificamos que no haya más emisiones.
            cancelAndConsumeRemainingEvents()
        }
    }
}
