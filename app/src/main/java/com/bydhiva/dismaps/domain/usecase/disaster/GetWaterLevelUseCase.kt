package com.bydhiva.dismaps.domain.usecase.disaster

import com.bydhiva.dismaps.base.Status
import com.bydhiva.dismaps.data.repository.DisasterRepository
import com.bydhiva.dismaps.domain.model.DisasterType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface GetWaterLevelUseCase {
    suspend operator fun invoke(): Flow<Status<String>>
}

class GetWaterLevelUseCaseImpl @Inject constructor(
    private val disasterRepository: DisasterRepository
) : GetWaterLevelUseCase {

    // Should use water level api, due inconsistent api and internal server error
    // use getReports flood depth as value
    override suspend fun invoke(): Flow<Status<String>> = flow {
        emit(Status.Loading())
        val reports = disasterRepository.getReports(
            disaster = DisasterType.FLOOD.toString().lowercase(),
        )
        var result = ""

        if (reports.isNotEmpty()) {
            result = reports.first().depth.toString()
        }
        emit(Status.Success(result))
    }.catch {
        t-> emit(Status.Error(t))
    }
}