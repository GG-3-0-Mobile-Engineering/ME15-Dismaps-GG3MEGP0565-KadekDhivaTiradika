package com.bydhiva.dismaps.domain.usecase.disaster

import com.bydhiva.dismaps.data.repository.DisasterRepository
import com.bydhiva.dismaps.domain.model.DisasterType
import javax.inject.Inject

interface GetWaterLevelUseCase {
    suspend operator fun invoke(): String
}

class GetWaterLevelUseCaseImpl @Inject constructor(
    private val disasterRepository: DisasterRepository
) : GetWaterLevelUseCase {

    // Should use water level api, due inconsistent api and internal server error
    // use getReports flood depth as value
    override suspend fun invoke(): String {
        val reports = disasterRepository.getReports(
            disaster = DisasterType.FLOOD.toString().lowercase(),
        )
        return reports.first().depth.toString()
    }
}