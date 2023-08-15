package com.bydhiva.dismaps.domain.usecase.setting

import com.bydhiva.dismaps.domain.model.SettingType
import com.bydhiva.dismaps.fake.FakeSettingRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

class GetSettingsUseCaseTest {
    private lateinit var settingRepository: FakeSettingRepository
    private lateinit var getSettingsUseCase: GetSettingsUseCase

    @Before
    fun setup() {
        settingRepository = FakeSettingRepository()
        getSettingsUseCase = GetSettingsUseCaseImpl(settingRepository)
    }

    @Test
    fun `when string key not valid, all setting value should return false`() = runTest {
        //Given
        val setting = getSettingsUseCase()

        //When
        val firstEmit = setting.first()

        //Then
        assertEquals(false, firstEmit.isDarkModeActive)
        assertEquals(false, firstEmit.isAlertActive)
    }

    @Test
    fun `when string key valid, all setting value should return true`() = runTest {
        //prepopulate repository with setting value
        settingRepository.saveBoolean(SettingType.DARK_MODE.toString(), true)
        settingRepository.saveBoolean(SettingType.WATER_LEVEL_ALERT.toString(), true)

        //Given
        val setting = getSettingsUseCase()

        //When
        val firstEmit = setting.first()

        //Then
        assertEquals(true, firstEmit.isDarkModeActive)
        assertEquals(true, firstEmit.isAlertActive)
    }

}