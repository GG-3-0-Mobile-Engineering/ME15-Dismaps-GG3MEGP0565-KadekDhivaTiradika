package com.bydhiva.dismaps.domain.usecase.disaster

import com.bydhiva.dismaps.base.Status
import com.bydhiva.dismaps.data.repository.DisasterRepository
import com.bydhiva.dismaps.domain.model.DisasterType
import com.bydhiva.dismaps.dummy.DummyReports
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

class GetWaterLevelUseCaseTest {
    private lateinit var disasterRepository: DisasterRepository
    private lateinit var getWaterLevelUseCase: GetWaterLevelUseCase

    @Before
    fun setup() {
        disasterRepository = FakeDisasterRepository()
        getWaterLevelUseCase = GetWaterLevelUseCaseImpl(disasterRepository)
    }

    @Test
    fun `when water level use case invoked, should return water level depth from live reports`() =
        runTest {
            //Given
            val waterLevel = getWaterLevelUseCase()

            //When
            val firstEmit = waterLevel.first()
            val lastEmit = waterLevel.last()

            //Then
            assert(firstEmit is Status.Loading)
            assert(lastEmit is Status.Success)

            //Validate water level depth
            val waterLevelDepth = DummyReports().getLiveReports().first {
                it.disasterType == DisasterType.FLOOD
            }.depth.toString()
            assertEquals(waterLevelDepth, lastEmit.data)
        }

    @Test
    fun `when failed to invoke water level use case, should return status error with throwable`() =
        runTest {
            //Given
            val disasterRepository = mockk<DisasterRepository>()
            val getWaterLevelUseCase = GetWaterLevelUseCaseImpl(disasterRepository)
            val waterLevel = getWaterLevelUseCase()
            coEvery { disasterRepository.getReports() } throws IOException()

            //When
            val firstEmit = waterLevel.first()
            val lastEmit = waterLevel.last()

            //Then
            assert(firstEmit is Status.Loading)
            assert(lastEmit is Status.Error)
            assert(lastEmit.error is Throwable)
        }
}