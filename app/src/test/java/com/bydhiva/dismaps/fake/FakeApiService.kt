package com.bydhiva.dismaps.fake

import com.bydhiva.dismaps.data.network.ApiService
import com.bydhiva.dismaps.data.network.response.ReportsResponse
import com.bydhiva.dismaps.dummy.DummyReportsResponse

class FakeApiService : ApiService {
    override suspend fun getReportsArchive(
        start: String,
        end: String,
        disaster: String?,
        provinceCode: String?
    ): ReportsResponse {
        return DummyReportsResponse.reportsResponse200
    }

    override suspend fun getReports(disaster: String?, provinceCode: String?): ReportsResponse {
        return DummyReportsResponse.reportsResponse200
    }
}