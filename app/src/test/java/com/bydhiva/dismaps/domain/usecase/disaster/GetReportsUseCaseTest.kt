package com.bydhiva.dismaps.domain.usecase.disaster

import com.bydhiva.dismaps.base.Status
import com.bydhiva.dismaps.common.ProvinceNotFoundException
import com.bydhiva.dismaps.data.repository.DisasterRepository
import com.bydhiva.dismaps.domain.model.DisasterType
import com.bydhiva.dismaps.dummy.DummyProvinces
import com.bydhiva.dismaps.fake.FakeDisasterRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.Instant
import java.util.Date
import java.util.Locale

class GetReportsUseCaseTest {
    private lateinit var disasterRepository: DisasterRepository
    private lateinit var getReportsUseCase: GetReportsUseCase

    @Before
    fun setup() {
        disasterRepository = FakeDisasterRepository()
        getReportsUseCase = GetReportsUseCaseImpl(disasterRepository)
    }

    @Test
    fun `when no filter applied, should return live reports`() =
        runTest {
            //Given
            val reports = getReportsUseCase()

            //When
            val firstEmit = reports.first()
            val lastEmit = reports.last()

            //Then
            assert(firstEmit is Status.Loading)
            assert(lastEmit is Status.Success)
            assert(!lastEmit.data.isNullOrEmpty())

            //Validate that all reports are from the past hour
            lastEmit.data?.forEach {
                val differenceHours = Duration.between(
                    it.createdAt?.toInstant(),
                    Instant.now()
                ).toHours()
                assert(differenceHours < 1)
            }
        }

    @Test
    fun `when disaster filter applied, should return live reports with only selected disaster`() =
        runTest {
            //Given
            val reports = getReportsUseCase(disasterType = DisasterType.FLOOD)

            //When
            val firstEmit = reports.first()
            val lastEmit = reports.last()

            //Then
            assert(firstEmit is Status.Loading)
            assert(lastEmit is Status.Success)
            assert(!lastEmit.data.isNullOrEmpty())

            //Validate that all reports are from the past hour
            //Validate all reports are flood
            lastEmit.data?.forEach {
                val differenceHours = Duration.between(
                    it.createdAt?.toInstant(),
                    Instant.now()
                ).toHours()
                assert(differenceHours < 1)
                assertEquals(it.disasterType, DisasterType.FLOOD)
            }
        }


    @Test
    fun `when date filter applied, should return archive reports with selected date range`() =
        runTest {
            //Given
            val startDate = "2023-08-01".parseDate()
            val endDate = "2023-08-15".parseDate()
            val reports = getReportsUseCase(startEndDate = Pair(startDate, endDate))

            //When
            val firstEmit = reports.first()
            val lastEmit = reports.last()

            //Then
            assert(firstEmit is Status.Loading)
            assert(lastEmit is Status.Success)
            assert(!lastEmit.data.isNullOrEmpty())

            //Validate all reports date is between given range
            lastEmit.data?.forEach {
                val isBetweenRange = it.createdAt?.after(startDate) ?: false &&
                        it.createdAt?.before(endDate) ?: false
                assert(isBetweenRange)
            }
        }

    @Test
    fun `when province query is valid, should return live reports only at given province area`() =
        runTest {
            //Given
            val searchQuery = "DKI Jakarta"
            val reports = getReportsUseCase(searchQuery = searchQuery)

            //When
            val firstEmit = reports.first()
            val lastEmit = reports.last()

            //Then
            assert(firstEmit is Status.Loading)
            assert(lastEmit is Status.Success)
            assert(!lastEmit.data.isNullOrEmpty())

            //Validate that all reports are from the past hour
            //Validate all reports are at given query area
            lastEmit.data?.forEach { disaster ->
                val differenceHours = Duration.between(
                    disaster.createdAt?.toInstant(),
                    Instant.now()
                ).toHours()
                val isAtProvinceArea = disaster.provinceCode == DummyProvinces.provinceList.find { it.name == searchQuery }?.code

                assert(differenceHours < 1)
                assert(isAtProvinceArea)
            }
        }

    @Test
    fun `when disaster and date filter applied, should return archive reports with only selected disaster and date range`() =
        runTest {
            //Given
            val startDate = "2023-08-01".parseDate()
            val endDate = "2023-08-15".parseDate()
            val reports = getReportsUseCase(
                disasterType = DisasterType.FLOOD,
                startEndDate = Pair(startDate, endDate)
            )

            //When
            val firstEmit = reports.first()
            val lastEmit = reports.last()

            //Then
            assert(firstEmit is Status.Loading)
            assert(lastEmit is Status.Success)
            assert(!lastEmit.data.isNullOrEmpty())

            //Validate all reports date is between given range
            //Validate all reports are flood
            lastEmit.data?.forEach {
                val isBetweenRange = it.createdAt?.after(startDate) ?: false &&
                        it.createdAt?.before(endDate) ?: false
                assert(isBetweenRange)
                assertEquals(it.disasterType, DisasterType.FLOOD)
            }
        }

    @Test
    fun `when disaster and province filter applied, should return live reports with only selected disaster at given province area`() =
        runTest {
            //Given
            val searchQuery = "DKI Jakarta"
            val reports = getReportsUseCase(
                disasterType = DisasterType.FLOOD,
                searchQuery = searchQuery
            )

            //When
            val firstEmit = reports.first()
            val lastEmit = reports.last()

            //Then
            assert(firstEmit is Status.Loading)
            assert(lastEmit is Status.Success)
            assert(!lastEmit.data.isNullOrEmpty())

            //Validate that all reports are from the past hour
            //Validate all reports are flood
            //Validate all reports are at given query area
            lastEmit.data?.forEach { disaster ->
                val differenceHours = Duration.between(
                    disaster.createdAt?.toInstant(),
                    Instant.now()
                ).toHours()
                val isAtProvinceArea = disaster.provinceCode == DummyProvinces.provinceList.find { it.name == searchQuery }?.code

                assert(differenceHours < 1)
                assert(isAtProvinceArea)
                assertEquals(disaster.disasterType, DisasterType.FLOOD)
            }
        }

    @Test
    fun `when province and date filter applied, should return archive reports between given date range at given province area`() =
        runTest {
            //Given
            val searchQuery = "DKI Jakarta"
            val startDate = "2023-08-01".parseDate()
            val endDate = "2023-08-15".parseDate()
            val reports = getReportsUseCase(
                startEndDate = Pair(startDate, endDate),
                searchQuery = searchQuery
            )

            //When
            val firstEmit = reports.first()
            val lastEmit = reports.last()

            //Then
            assert(firstEmit is Status.Loading)
            assert(lastEmit is Status.Success)
            assert(!lastEmit.data.isNullOrEmpty())

            //Validate all reports date is between given range
            //Validate all reports are at given query area
            lastEmit.data?.forEach {disaster ->
                val isBetweenRange = disaster.createdAt?.after(startDate) ?: false &&
                        disaster.createdAt?.before(endDate) ?: false
                val isAtProvinceArea = disaster.provinceCode == DummyProvinces.provinceList.find { it.name == searchQuery }?.code

                assert(isBetweenRange)
                assert(isAtProvinceArea)
            }
        }

    @Test
    fun `when disaster, province and date filter applied, should return archive reports between given date range at given province area and only selected disaster`() =
        runTest {
            //Given
            val searchQuery = "DKI Jakarta"
            val startDate = "2023-08-01".parseDate()
            val endDate = "2023-08-15".parseDate()
            val reports = getReportsUseCase(
                startEndDate = Pair(startDate, endDate),
                searchQuery = searchQuery,
                disasterType = DisasterType.FLOOD
            )

            //When
            val firstEmit = reports.first()
            val lastEmit = reports.last()

            //Then
            assert(firstEmit is Status.Loading)
            assert(lastEmit is Status.Success)
            assert(!lastEmit.data.isNullOrEmpty())

            //Validate all reports date is between given range
            //Validate all reports are at given query area
            //Validate all reports are flood
            lastEmit.data?.forEach { disaster ->
                val isBetweenRange = disaster.createdAt?.after(startDate) ?: false &&
                        disaster.createdAt?.before(endDate) ?: false
                val isAtProvinceArea = disaster.provinceCode == DummyProvinces.provinceList.find { it.name == searchQuery }?.code

                assert(isBetweenRange)
                assert(isAtProvinceArea)
                assertEquals(disaster.disasterType, DisasterType.FLOOD)
            }
        }

    @Test
    fun `when province query not valid, should return error status with throwable`() =
        runTest {
            //Given
            val searchQuery = "Indonesia"
            val reports = getReportsUseCase(searchQuery = searchQuery)

            //When
            val firstEmit = reports.first()
            val lastEmit = reports.last()

            //Then
            assert(firstEmit is Status.Loading)
            assert(lastEmit is Status.Error)
            assert(lastEmit.error is ProvinceNotFoundException)
        }

    @Test
    fun `when request reports failed, should return error status with throwable`() =
        runTest {
            //Given
            val disasterRepository = mockk<DisasterRepository>()
            val getReportsUseCase = GetReportsUseCaseImpl(disasterRepository)
            coEvery { disasterRepository.getReports() } throws IOException()

            val reports = getReportsUseCase()

            //When
            val firstEmit = reports.first()
            val lastEmit = reports.last()

            //Then
            assert(firstEmit is Status.Loading)
            assert(lastEmit is Status.Error)
            assert(lastEmit.error is Throwable)

        }

    private fun String.parseDate(): Date {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        return formatter.parse(this) ?: Date()
    }
}




