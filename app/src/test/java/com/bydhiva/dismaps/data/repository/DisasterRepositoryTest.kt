package com.bydhiva.dismaps.data.repository

import com.bydhiva.dismaps.common.NetworkServerErrorException
import com.bydhiva.dismaps.common.toDisaster
import com.bydhiva.dismaps.data.network.ApiService
import com.bydhiva.dismaps.dummy.DummyReportsResponse
import com.bydhiva.dismaps.fake.FakeApiService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows

class DisasterRepositoryTest {
    private lateinit var apiService: ApiService
    private lateinit var disasterRepository: DisasterRepository

    @Before
    fun setup(){
        apiService = FakeApiService()
        disasterRepository = DisasterRepositoryImpl(apiService)
    }

    @Test
    fun `when status 200, live reports response should mapped to disaster model`() = runTest {
        //Given
        val disasters = disasterRepository.getReports()

        //When
        val expected = DummyReportsResponse.reportsResponse200.result.objects?.output?.geometries?.map {
            it?.toDisaster()
        }

        //Then
        assertEquals(expected, disasters)
    }

    @Test
    fun `when status 200, archive reports response should mapped to disaster model`() = runTest {
        //Given
        val disasters = disasterRepository.getReportsArchive(
            start = "2023-08-01T08%3A00%3A00Z",
            end = "2023-08-01T20%3A00%3A00Z"
        )

        //When
        val expected = DummyReportsResponse.reportsResponse200.result.objects?.output?.geometries?.map {
            it?.toDisaster()
        }

        //Then
        assertEquals(expected, disasters)
    }

    @Test
    fun `when status 500, get reports should throw network server error exception`() = runTest {
        //Given
        val apiService = mockk<ApiService>()
        val disasterRepository = DisasterRepositoryImpl(apiService)

        //When
        coEvery { apiService.getReports() } returns DummyReportsResponse.reportsResponse500

        //Then
        assertThrows<NetworkServerErrorException> {
            disasterRepository.getReports()
        }
    }

    @Test
    fun `when status 500, get reports archive should throw network server error exception`() = runTest {
        //Given
        val apiService = mockk<ApiService>()
        val disasterRepository = DisasterRepositoryImpl(apiService)

        //When
        coEvery {
            apiService.getReportsArchive("2023-08-01T08%3A00%3A00Z", "2023-08-01T20%3A00%3A00Z")
        } returns DummyReportsResponse.reportsResponse500

        //Then
        assertThrows<NetworkServerErrorException> {
            disasterRepository.getReportsArchive("2023-08-01T08%3A00%3A00Z", "2023-08-01T20%3A00%3A00Z")
        }
    }

    @Test
    fun `when status 200 but result empty, get reports should return empty list`() = runTest {
        //Given
        val apiService = mockk<ApiService>()
        val disasterRepository = DisasterRepositoryImpl(apiService)

        //When
        coEvery { apiService.getReports() } returns DummyReportsResponse.reportsResponseEmpty200
        val result = disasterRepository.getReports()

        //Then
        assert(result.isEmpty())
    }

    @Test
    fun `when status 200 but result empty, get reports archive should return empty list`() = runTest {
        //Given
        val apiService = mockk<ApiService>()
        val disasterRepository = DisasterRepositoryImpl(apiService)

        //When
        coEvery {
            apiService.getReportsArchive("2023-08-01T08%3A00%3A00Z", "2023-08-01T20%3A00%3A00Z")
        } returns DummyReportsResponse.reportsResponseEmpty200
        val result = disasterRepository.getReportsArchive("2023-08-01T08%3A00%3A00Z", "2023-08-01T20%3A00%3A00Z")

        //Then
        assert(result.isEmpty())
    }
}