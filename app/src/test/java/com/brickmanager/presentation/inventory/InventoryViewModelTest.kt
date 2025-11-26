package com.brickmanager.presentation.inventory

import app.cash.turbine.test
import com.brickmanager.domain.entity.Set
import com.brickmanager.domain.usecase.AddSetToInventoryUseCase
import com.brickmanager.domain.usecase.GetSetInventoryUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
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
    @MockK
    private lateinit var mockAddSetToInventoryUseCase: AddSetToInventoryUseCase

    private lateinit var viewModel: InventoryViewModel

    // 1. Usamos StandardTestDispatcher para controlar la ejecución
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)

        val fakeSets = listOf(Set(id = "10294", name = "Titanic", series = "Creator", pieceCount = 9090, isBuilt = false, imageUrl = ""))
        every { mockGetSetInventoryUseCase.invoke() } returns flowOf(fakeSets)

        viewModel = InventoryViewModel(mockGetSetInventoryUseCase, mockAddSetToInventoryUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadInventory should transition through loading to success state`() = runTest {
        // ARRANGE: Inicializamos el ViewModel. Su bloque init{} agenda la corrutina, pero NO la ejecuta aún.
        viewModel = InventoryViewModel(mockGetSetInventoryUseCase, mockAddSetToInventoryUseCase)

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

    @Test
    fun `onAddSetClicked should call AddSetToInventoryUseCase`() = runTest {
        // ARRANGE
        val setIdToAdd = "75301"
        coEvery { mockAddSetToInventoryUseCase(any()) } returns Unit
        viewModel = InventoryViewModel(mockGetSetInventoryUseCase, mockAddSetToInventoryUseCase)

        // ACT
        viewModel.onAddSetClicked(setIdToAdd)
        // Avanzamos el dispatcher para que se ejecute la corrutina lanzada en el ViewModel
        testDispatcher.scheduler.advanceUntilIdle()

        // ASSERT
        // Verificamos que el caso de uso fue llamado exactamente una vez con el ID correcto.
        coVerify(exactly = 1) { mockAddSetToInventoryUseCase(setIdToAdd) }
    }

    @Test
    fun `onAddSetClicked should set errorMessage if validation fails`() = runTest {
        val expectedError = "Set ID cannot be empty or blank."
        val invalidId = ""

        // ARRANGE
        // 1. Configura el mock para que lance la excepción esperada.
        coEvery { mockAddSetToInventoryUseCase(invalidId) } throws IllegalArgumentException(expectedError)

        // 2. Inicializa el ViewModel. El loadInventory() inicial se agenda.
        viewModel = InventoryViewModel(mockGetSetInventoryUseCase, mockAddSetToInventoryUseCase)

        // 3. Ejecuta la carga inicial para tener un estado base limpio.
        testDispatcher.scheduler.advanceUntilIdle()

        // ACT
        // 4. Llama a la función que quieres probar. Esto agenda la corrutina del onClick.
        viewModel.onAddSetClicked(invalidId)

        // 5. ¡Paso Clave! Ejecuta la corrutina del onClick.
        // Ahora, el bloque catch en el ViewModel se ejecuta y actualiza el uiState con el error.
        testDispatcher.scheduler.advanceUntilIdle()

        // ASSERT
        // 6. Ahora que el StateFlow ya está actualizado, verificamos su contenido.
        viewModel.uiState.test {
            // El primer item que recibimos ya es el estado más reciente, que contiene el error.
            val errorState = awaitItem()
            assertEquals(expectedError, errorState.errorMessage)

            // Verificamos que no haya más emisiones.
            cancelAndConsumeRemainingEvents()
        }
    }

}
