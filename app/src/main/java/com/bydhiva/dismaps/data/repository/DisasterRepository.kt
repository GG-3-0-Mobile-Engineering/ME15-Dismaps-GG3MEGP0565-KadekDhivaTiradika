package com.bydhiva.dismaps.data.repository

import com.bydhiva.dismaps.common.getExceptionByCode
import com.bydhiva.dismaps.common.requestSuccessCode
import com.bydhiva.dismaps.common.toDisaster
import com.bydhiva.dismaps.data.network.ApiService
import com.bydhiva.dismaps.domain.model.Disaster

interface DisasterRepository {
    suspend fun getReports(
        disaster: String? = null,
        provinceCode: String? = null
    ) : List<Disaster>

    suspend fun getReportsArchive(
        start: String,
        end: String,
        disaster: String? = null,
        provinceCode: String? = null
    ) : List<Disaster>
}

class DisasterRepositoryImpl(
    private val apiService: ApiService
): DisasterRepository {

    override suspend fun getReports(disaster: String?, provinceCode: String?): List<Disaster> {
        val reportsResponse = apiService.getReports(disaster, provinceCode)
        if (reportsResponse.statusCode != requestSuccessCode)
            throw getExceptionByCode(reportsResponse.statusCode)
        val listReport = reportsResponse.result.objects?.output?.geometries?.mapNotNull {
            it?.toDisaster()
        } ?: listOf()
        return listReport
    }

    override suspend fun getReportsArchive(
        start: String,
        end: String,
        disaster: String?,
        provinceCode: String?
    ): List<Disaster> {
        val reportsResponse = apiService.getReportsArchive(start, end, disaster, provinceCode)
        if (reportsResponse.statusCode != requestSuccessCode)
            throw getExceptionByCode(reportsResponse.statusCode)
        val listReport = reportsResponse.result.objects?.output?.geometries?.mapNotNull {
            it?.toDisaster()
        } ?: listOf()
        return listReport
    }

}