package com.bydhiva.dismaps.domain.usecase.setting

import com.bydhiva.dismaps.data.repository.SettingRepository
import com.bydhiva.dismaps.domain.model.SettingType
import com.bydhiva.dismaps.fake.FakeSettingRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class SaveSettingUseCaseTest {
    private lateinit var settingRepository: FakeSettingRepository
    private lateinit var saveSettingsUseCase: SaveSettingUseCase

    @Before
    fun setup() {
        settingRepository = FakeSettingRepository()
        saveSettingsUseCase = SaveSettingUseCaseImpl(settingRepository)
    }

    @Test
    fun `when save setting use case invoked, boolean value should saved with string key`() = runTest {
        //When
        saveSettingsUseCase.saveBoolean(SettingType.DARK_MODE, true)

        //Then
        val actualResult = settingRepository.getSettingData().getValue(SettingType.DARK_MODE.toString())
        assertEquals(true, actualResult)
    }
}