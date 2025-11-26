package com.brickmanager.data.repository

import com.brickmanager.data.dao.SetDao
import com.brickmanager.data.entity.SetEntity
import com.brickmanager.data.remote.RebrickableApiService
import com.brickmanager.data.remote.model.RebrickableSetResponse
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
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class InventoryRepositoryImplTest {

    @MockK
    private lateinit var mockSetDao: SetDao

    @MockK
    private lateinit var mockApiService: RebrickableApiService

    private lateinit var repository: InventoryRepositoryImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repository = InventoryRepositoryImpl(mockSetDao, mockApiService)
    }

    @Test
    fun `getSetInventory should map SetEntity to Domain Set correctly`() = runTest {
        // ARRANGE
        val entityList = listOf(
            SetEntity(
                id = "42115-1",
                name = "Lamborghini Sián FKP 37",
                pieceCount = 3696,
                isBuilt = true,
                imageUrl = null
            )
        )
        coEvery { mockSetDao.getSetInventory() } returns flowOf(entityList)

        // ACT
        val result = repository.getSetInventory().first()

        // ASSERT
        assertEquals(1, result.size)
        val expectedDomainSet = Set(
            id = "42115-1",
            name = "Lamborghini Sián FKP 37",
            series = "TODO", // This comes from the mapper's placeholder
            pieceCount = 3696,
            isBuilt = true,
            imageUrl = null
        )
        assertEquals(expectedDomainSet, result.first())
    }

    @Test
    fun `addSet should call apiService and insert mapped entity into DAO`() = runTest {
        val testSetId = "42115-1"

        // ARRANGE: Define the response the API mock should give
        val apiResponse = RebrickableSetResponse(
            set_num = testSetId,
            name = "Lamborghini Sián",
            num_parts = 3696,
            year = 2020,
            set_img_url = null,
            last_modified_dt = ""
        )
        coEvery { mockApiService.getSetDetails(testSetId, any()) } returns apiResponse

        coEvery { mockSetDao.insertSet(any()) } returns Unit

        // ACT
        repository.addSet(testSetId)

        // ASSERT 1: Verify the API service was called with the correct set number
        coVerify(exactly = 1) { mockApiService.getSetDetails(testSetId, any()) }

        // ASSERT 2: Verify the DAO was called with the correctly MAPPED entity.
        val expectedEntity = SetEntity(
            id = testSetId,
            name = "Lamborghini Sián",
            pieceCount = 3696,
            isBuilt = false,
            imageUrl = null
        )
        coVerify(exactly = 1) { mockSetDao.insertSet(expectedEntity) }
    }

    @Test(expected = IOException::class)
    fun `addSet should throw IOException when API call fails due to network error`() = runTest {
        val testSetId = "99999-1"

        // ARRANGE: Simulate a network failure
        coEvery { mockApiService.getSetDetails(testSetId, any()) } throws IOException()

        // ACT
        repository.addSet(testSetId)
    }

    @Test(expected = Exception::class)
    fun `addSet should throw descriptive Exception when API returns 404 Not Found`() = runTest {
        val testSetId = "00000-1"

        // ARRANGE: Simulate a 404 Not Found error
        val errorResponse = Response.error<Any>(404, okhttp3.ResponseBody.create(null, ""))
        coEvery { mockApiService.getSetDetails(testSetId, any()) } throws HttpException(errorResponse)

        // ACT
        repository.addSet(testSetId)
    }
}