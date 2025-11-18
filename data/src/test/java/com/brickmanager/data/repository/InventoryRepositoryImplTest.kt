package com.brickmanager.data.repository

import com.brickmanager.data.dao.SetDao
import com.brickmanager.data.entity.SetEntity
import com.brickmanager.domain.entity.Set
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class InventoryRepositoryImplTest {

    // Declaramos el mock para la dependencia (la interfaz de Room)
    @MockK
    private lateinit var mockSetDao: SetDao
    
    // La instancia real que vamos a probar
    private lateinit var repository: InventoryRepositoryImpl

    @Before
    fun setup() {
        // Inicializa los mocks anotados
        MockKAnnotations.init(this)
        // Crea la instancia real, inyectando el mock del DAO
        repository = InventoryRepositoryImpl(mockSetDao)
    }

    @Test
    fun `getSetInventory should map SetEntity to Domain Set correctly`() = runTest {
        // ARRANGE (Preparar): 
        
        // 1. Crear una lista de entidades de datos (lo que simula devolver Room)
        val entityList = listOf(
            SetEntity(
                id = "42115", 
                name = "Lamborghini Sián FKP 37", 
                pieceCount = 3696, 
                isBuilt = true, 
                estimatedMarketValue = 379.99
            )
        )
        
        // 2. Definir el comportamiento del Mock: Cuando se llame a getSetInventory en el DAO, 
        // debe devolver el Flow de nuestra lista de entidades.
        coEvery { mockSetDao.getSetInventory() } returns flowOf(entityList)

        // ACT (Actuar): Llamar a la función del repositorio y obtener el primer valor del Flow.
        val result = repository.getSetInventory().first() 

        // ASSERT (Afirmar): 
        
        // 1. Verificar el tamaño
        assertEquals(1, result.size)
        
        // 2. Verificar que el mapeo fue correcto para la entidad de Dominio
        val expectedDomainSet = Set(
            id = "42115",
            name = "Lamborghini Sián FKP 37",
            series = "TODO", // Coincide con la simulación en el Mapper
            pieceCount = 3696,
            isBuilt = true,
            estimatedMarketValue = 379.99
        )
        
        assertEquals(expectedDomainSet, result.first())
    }

    @Test
    fun `addSet should insert a SetEntity into the DAO`() = runTest {
        // ARRANGE
        val testSetId = "75301"
        
        // Creamos una entidad que esperamos que sea insertada (simulando el mapeo)
        // Nota: El repositorio actualmente usa datos quemados (hardcoded) para la simulación
        // de datos remotos, por lo que la entidad esperada debe coincidir con esa simulación.
        val expectedEntity = SetEntity(
            id = testSetId, 
            name = "Simulated Set 75301", 
            pieceCount = 100, 
            isBuilt = false, 
            estimatedMarketValue = 49.99
        )

        // No definimos comportamiento para el mock, pero SÍ verificaremos que se llamó a la inserción.

        // ACT
        repository.addSet(testSetId)

        // ASSERT: Verificar que el metodo 'insertSet' del DAO fue llamado con la entidad esperada.
        coVerify(exactly = 1) { mockSetDao.insertSet(expectedEntity) } 
    }
}