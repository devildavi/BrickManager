package com.brickmanager.data.repository

import com.brickmanager.data.dao.SetDao
import com.brickmanager.data.entity.SetEntity
import com.brickmanager.data.remote.RebrickableApiService
import com.brickmanager.data.remote.model.RebrickableMinifigResponse
import com.brickmanager.data.remote.model.RebrickableSetResponse
import com.brickmanager.data.remote.model.RebrickableThemeResponse
import com.brickmanager.domain.entity.Set
import com.brickmanager.data.utils.getCurrentDate
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
                series = "Technic",
                pieceCount = 3696,
                minifigCount = 0,
                isBuilt = true,
                imageUrl = null,
                acquisitionDate = "2023-10-27",
                buildDate = "2023-10-28"
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
            series = "Technic",
            pieceCount = 3696,
            minifigCount = 0,
            isBuilt = true,
            imageUrl = null,
            acquisitionDate = "2023-10-27",
            buildDate = "2023-10-28"
        )
        assertEquals(expectedDomainSet, result.first())
    }

    @Test
    fun `call all APIs and insert mapped entity into DAO`() = runTest {
        val testSetId = "75301-1"

        // ARRANGE: Define mock responses for all API calls
        val setDetailsResponse = RebrickableSetResponse(
            set_num = testSetId,
            name = "Luke Skywalker\'s X-Wing Fighter",
            num_parts = 474,
            year = 2021,
            set_img_url = "image_url",
            theme_id = 158, // Star Wars theme ID
            last_modified_dt = ""
        )
        val minifigResponse = RebrickableMinifigResponse(count = 4, results = emptyList())
        val themeResponse = RebrickableThemeResponse(id = 158, name = "Star Wars", parent_id = null)

        coEvery { mockApiService.getSetDetails(testSetId, any()) } returns setDetailsResponse
        coEvery { mockApiService.getMinifiguresForSet(testSetId, any()) } returns minifigResponse
        coEvery { mockApiService.getThemeDetails(158, any()) } returns themeResponse
        coEvery { mockSetDao.insertSet(any()) } returns Unit

        // ACT
        repository.addSet("75301") // Use the ID without suffix, as the user would

        // ASSERT: Verify all API services were called
        coVerify(exactly = 1) { mockApiService.getSetDetails(testSetId, any()) }
        coVerify(exactly = 1) { mockApiService.getMinifiguresForSet(testSetId, any()) }
        coVerify(exactly = 1) { mockApiService.getThemeDetails(158, any()) }

        // ASSERT: Verify the DAO was called with the correctly combined entity
        val expectedEntity = SetEntity(
            id = testSetId,
            name = "Luke Skywalker\'s X-Wing Fighter",
            series = "Star Wars",
            pieceCount = 474,
            minifigCount = 4,
            isBuilt = false,
            imageUrl = "image_url",
            acquisitionDate = getCurrentDate(), // Check against the real date
            buildDate = null
        )
        coVerify(exactly = 1) { mockSetDao.insertSet(expectedEntity) }
    }

    @Test(expected = IOException::class)
    fun `addSet should throw IOException when API call fails due to network error`() = runTest {
        val testSetId = "99999-1"
        coEvery { mockApiService.getSetDetails(any(), any()) } throws IOException()
        repository.addSet(testSetId)
    }

    @Test(expected = Exception::class)
    fun `addSet should throw descriptive Exception when API returns 404 Not Found`() = runTest {
        val testSetId = "00000-1"
        val errorResponse = Response.error<Any>(404, okhttp3.ResponseBody.create(null, ""))
        coEvery { mockApiService.getSetDetails(any(), any()) } throws HttpException(errorResponse)
        repository.addSet(testSetId)
    }
}