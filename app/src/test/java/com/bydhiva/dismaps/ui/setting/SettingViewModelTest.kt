package com.bydhiva.dismaps.ui.setting

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.work.WorkManager
import com.bydhiva.dismaps.data.repository.SettingRepository
import com.bydhiva.dismaps.data.repository.SettingRepositoryImpl
import com.bydhiva.dismaps.domain.model.Setting
import com.bydhiva.dismaps.domain.usecase.setting.GetSettingsUseCase
import com.bydhiva.dismaps.domain.usecase.setting.GetSettingsUseCaseImpl
import com.bydhiva.dismaps.domain.usecase.setting.SaveSettingUseCase
import com.bydhiva.dismaps.domain.usecase.setting.SaveSettingUseCaseImpl
import com.bydhiva.dismaps.testutils.MainCoroutineRule
import com.bydhiva.dismaps.testutils.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.rules.TemporaryFolder

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockKExtension::class)
class SettingViewModelTest {
    @get:Rule
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()
    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher + Job())
    private val testDataStore: DataStore<Preferences> = PreferenceDataStoreFactory.create(
        scope = testScope,
        produceFile = { tmpFolder.newFile("testSetting.preferences_pb") }
    )
    private lateinit var settingRepository: SettingRepository
    private lateinit var getSettingUseCase: GetSettingsUseCase
    private lateinit var saveSettingUseCase: SaveSettingUseCase
    private lateinit var settingViewModel: SettingViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var workManager: WorkManager

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        settingRepository = SettingRepositoryImpl(testDataStore)
        getSettingUseCase = GetSettingsUseCaseImpl(settingRepository)
        saveSettingUseCase = SaveSettingUseCaseImpl(settingRepository)
        settingViewModel = SettingViewModel(getSettingUseCase, saveSettingUseCase, workManager)
    }

    @Test
    fun `setting value should saved on data store preferences`() = runTest {
        //Given
        settingViewModel.saveAlertSetting(true)
        settingViewModel.saveThemeSetting(true)

        //When
        val settings = settingViewModel.settings.getOrAwaitValue()

        //Then
        assertEquals(Setting(isDarkModeActive = true, isAlertActive = true), settings)
    }
}