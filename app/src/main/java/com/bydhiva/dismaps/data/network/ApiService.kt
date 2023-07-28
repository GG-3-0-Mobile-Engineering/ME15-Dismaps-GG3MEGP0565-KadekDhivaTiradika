package com.bydhiva.dismaps.data.network

import com.bydhiva.dismaps.data.network.response.ReportsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("reports/archive")
    suspend fun getReportsArchive(
        @Query("start") start: String,
        @Query("end") end: String,
        @Query("disaster") disaster: String? = null,
        @Query("admin") provinceCode: String? = null
    ): ReportsResponse

    @GET("reports")
    suspend fun getReports(
        @Query("disaster") disaster: String? = null,
        @Query("admin") provinceCode: String? = null
    ): ReportsResponse
}
