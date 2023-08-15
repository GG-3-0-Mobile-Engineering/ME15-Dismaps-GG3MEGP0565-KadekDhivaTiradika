package com.bydhiva.dismaps.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.bydhiva.dismaps.domain.model.SettingType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.rules.TemporaryFolder


@OptIn(ExperimentalCoroutinesApi::class)
class SettingRepositoryTest {
    @get:Rule
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()
    private val testDispatcher = UnconfinedTestDispatcher()
    private val testScope = TestScope(testDispatcher + Job())
    private val testDataStore: DataStore<Preferences> = PreferenceDataStoreFactory.create(
        scope = testScope,
        produceFile = { tmpFolder.newFile("testSetting.preferences_pb") }
    )
    private lateinit var settingRepository: SettingRepository

    @Before
    fun setup() {
        settingRepository = SettingRepositoryImpl(testDataStore)
    }

    @Test
    fun `save boolean value, should return true when get boolean with same key`() = runTest {
        //Given
        settingRepository.saveBoolean(SettingType.DARK_MODE.toString(), true)

        //When
        val firstEmit = settingRepository.getBoolean(SettingType.DARK_MODE.toString()).first()

        //Then
        assertEquals(true, firstEmit)
    }

}