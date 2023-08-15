package com.bydhiva.dismaps.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bydhiva.dismaps.base.Status
import com.bydhiva.dismaps.data.repository.DisasterRepository
import com.bydhiva.dismaps.domain.usecase.disaster.GetReportsUseCase
import com.bydhiva.dismaps.domain.usecase.disaster.GetReportsUseCaseImpl
import com.bydhiva.dismaps.fake.FakeDisasterRepository
import org.junit.Before
import org.junit.Rule
import org.junit.jupiter.api.Assertions.*
import com.bydhiva.dismaps.domain.model.DisasterType
import com.bydhiva.dismaps.domain.model.ReportsFilter
import com.bydhiva.dismaps.dummy.DummyReports
import com.bydhiva.dismaps.testutils.MainCoroutineRule
import com.bydhiva.dismaps.testutils.getOrAwaitValue
import com.bydhiva.dismaps.testutils.parseDate
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.concurrent.thread

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {
    private lateinit var disasterRepository: DisasterRepository
    private lateinit var getReportsUseCase: GetReportsUseCase
    private lateinit var mainViewModel: MainViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        disasterRepository = FakeDisasterRepository()
        getReportsUseCase = GetReportsUseCaseImpl(disasterRepository)
        mainViewModel = MainViewModel(getReportsUseCase)
    }

    @Test
    fun `when get reports success and not empty, should post value to main UI succes event`() {
        //Given
        //set filter
        val searchQuery = "DKI Jakarta"
        val startDate = "2023-08-01".parseDate()
        val endDate = "2023-08-15".parseDate()
        val disasterType = DisasterType.FLOOD
        mainViewModel.setSearchText(searchQuery)
        mainViewModel.setDateFilter(Pair(startDate, endDate))
        mainViewModel.setDisasterTypeFilter(disasterType)

        //When
        mainViewModel.getReports()
        val mainUiEvent = mainViewModel.mainUIEvent.getOrAwaitValue()
        val isLoading = mainViewModel.isListLoading.getOrAwaitValue()
        val listDisaster = mainViewModel.listDisaster.getOrAwaitValue()

        //Then
        assertEquals(false, isLoading)
        assert(mainUiEvent is MainViewModel.MainUIEvent.SuccessEvent)
        assert(listDisaster.isNotEmpty())
    }

    @Test
    fun `when get reports success and empty, should post value to main UI empty event`() {
        //Given
        //set filter
        val searchQuery = "DKI Jakarta"
        val startDate = "2023-07-01".parseDate()
        val endDate = "2023-07-15".parseDate()
        val disasterType = DisasterType.FLOOD
        mainViewModel.setSearchText(searchQuery)
        mainViewModel.setDateFilter(Pair(startDate, endDate))
        mainViewModel.setDisasterTypeFilter(disasterType)

        //When
        mainViewModel.getReports()
        val mainUiEvent = mainViewModel.mainUIEvent.getOrAwaitValue()
        val isLoading = mainViewModel.isListLoading.getOrAwaitValue()

        //Then
        assertEquals(false, isLoading)
        assertTrue(mainUiEvent is MainViewModel.MainUIEvent.EmptyEvent)
    }

    @Test
    fun `when get reports failed, should post value to main UI error event`() {
        //Given
        //set filter
        val searchQuery = "Indonesia"
        val startDate = "2023-07-01".parseDate()
        val endDate = "2023-07-15".parseDate()
        val disasterType = DisasterType.FLOOD
        mainViewModel.setSearchText(searchQuery)
        mainViewModel.setDateFilter(Pair(startDate, endDate))
        mainViewModel.setDisasterTypeFilter(disasterType)

        //When
        mainViewModel.getReports()
        val mainUiEvent = mainViewModel.mainUIEvent.getOrAwaitValue()
        val isLoading = mainViewModel.isListLoading.getOrAwaitValue()

        //Then
        assertEquals(false, isLoading)
        assert(mainUiEvent is MainViewModel.MainUIEvent.ErrorEvent)
    }

    @Test
    fun `when disaster selected, should post value to selected disaster live data`() {
        //Given
        val dummyDisaster = DummyReports().getLiveReports().first()

        //When
        mainViewModel.setSelectedDisaster(dummyDisaster)

        //Then
        val disaster = mainViewModel.selectedDisaster.getOrAwaitValue()
        assertEquals(dummyDisaster, disaster)
    }
}