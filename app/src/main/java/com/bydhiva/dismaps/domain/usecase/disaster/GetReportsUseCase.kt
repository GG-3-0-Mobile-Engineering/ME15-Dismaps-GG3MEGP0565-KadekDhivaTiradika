package com.bydhiva.dismaps.domain.usecase.disaster

import com.bydhiva.dismaps.base.Status
import com.bydhiva.dismaps.common.ProvinceNotFoundException
import com.bydhiva.dismaps.data.repository.DisasterRepository
import com.bydhiva.dismaps.domain.model.Disaster
import com.bydhiva.dismaps.domain.model.DisasterType
import com.bydhiva.dismaps.utils.getProvinceCode
import com.bydhiva.dismaps.utils.toStringISO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.util.Date
import javax.inject.Inject

interface GetReportsUseCase {
    operator fun invoke(
        startEndDate: Pair<Date, Date>? = null,
        searchQuery: String,
        disasterType: DisasterType? = null
    ): Flow<Status<List<Disaster>>>
}

class GetReportsUseCaseImpl @Inject constructor(
    private val disasterRepository: DisasterRepository
) : GetReportsUseCase {
    override fun invoke(
        startEndDate: Pair<Date, Date>?,
        searchQuery: String,
        disasterType: DisasterType?
    ) = flow {
        emit(Status.Loading())
        if (searchQuery.isNotEmpty() && searchQuery.getProvinceCode() == null)
            throw ProvinceNotFoundException()
        val result  = if (startEndDate == null) {
            disasterRepository.getReports(
                disaster = disasterType?.toString()?.lowercase(),
                provinceCode = searchQuery.getProvinceCode()
            )
        } else {
            disasterRepository.getReportsArchive(
                start = startEndDate.first.toStringISO(),
                end = startEndDate.second.toStringISO(),
                disaster = disasterType?.toString()?.lowercase(),
                provinceCode = searchQuery.getProvinceCode()
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