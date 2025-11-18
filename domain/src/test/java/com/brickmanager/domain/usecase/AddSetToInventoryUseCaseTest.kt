package com.brickmanager.domain.usecase

import com.brickmanager.domain.repository.InventoryRepository
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AddSetToInventoryUseCaseTest {

    // 1. Declarar el Mock: Creamos una versión simulada de la dependencia.
    private lateinit var mockRepository: InventoryRepository

    // 2. Setup: Inicializa el mock antes de cada test.
    @Before
    fun setup() {
        mockRepository = mockk(relaxed = true) // 'coMockk' para funciones suspend
    }

    @Test
    fun `when invoking use case, repository_addSet should be called with correct ID`() = runTest {
        // ARRANGE (Preparar): Define el ID que vamos a usar.
        val testSetId = "75301"
        
        // ACT (Actuar): Instanciar el Use Case e invocarlo.
        // La dependencia inyectada es el mock.
        val useCase = AddSetToInventoryUseCase(mockRepository)
        useCase.invoke(testSetId) // o useCase(testSetId) si usas 'operator fun invoke'

        // ASSERT (Afirmar): Verificar que el método del mock fue llamado.
        // ESTE ES EL PASO CLAVE DEL TDD: Verificar el comportamiento esperado.
        coVerify(exactly = 1) { mockRepository.addSet(testSetId) }
        
        // OPCIONAL: Asegura que ninguna otra función del repositorio fue llamada.
        confirmVerified(mockRepository) 
    }

    // --- NUEVO TEST AÑADIDO ---
    @Test(expected = IllegalArgumentException::class) // 1. Esperamos que esta excepción sea lanzada.
    fun `when set ID is blank, it should throw IllegalArgumentException and not call repository`() = runTest {
        // ARRANGE
        val testBlankId = " "
        val useCase = AddSetToInventoryUseCase(mockRepository)

        // ACT
        useCase(testBlankId) // 2. Llama al Use Case con el dato inválido.

        // ASSERT (Implícito por @Test(expected)): Si la excepción se lanza, el test pasa.
        // Además, verificamos que la función clave del repositorio NUNCA fue llamada.
        coVerify(exactly = 0) { mockRepository.addSet(any()) }
    }
}