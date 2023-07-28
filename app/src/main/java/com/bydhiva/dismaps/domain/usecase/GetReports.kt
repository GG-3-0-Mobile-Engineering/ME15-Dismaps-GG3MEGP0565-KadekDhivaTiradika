package com.bydhiva.dismaps.domain.usecase

import com.bydhiva.dismaps.base.Status
import com.bydhiva.dismaps.common.BadRequestNetworkException
import com.bydhiva.dismaps.data.DisasterRepository
import com.bydhiva.dismaps.domain.model.DisasterType
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class GetReports(
    private val disasterRepository: DisasterRepository
) {
    operator fun invoke(
        start: String? = null,
        end: String? = null,
        provinceCode: String? = null,
        disasterType: DisasterType? = null
    ) = flow {
        emit(Status.Loading())
        val result  = if (start == null || end == null) {
            disasterRepository.getReports(
                disaster = disasterType?.toString()?.lowercase(),
                provinceCode = provinceCode
            )
        } else {
            disasterRepository.getReportsArchive(
                start = start,
                end = end,
                disaster = disasterType?.toString()?.lowercase(),
                provinceCode = provinceCode
            )
        }
        val listReport = disasterType?.let { type ->
            result.filter {
                it.disasterType == type
            }
        } ?: result
        emit(Status.Success(listReport))
    }.catch {
        t -> emit(Status.Error(t))
    }
}