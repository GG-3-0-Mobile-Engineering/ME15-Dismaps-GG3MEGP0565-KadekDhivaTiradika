package com.bydhiva.dismaps.dummy

import com.bydhiva.dismaps.common.toDisaster
import com.bydhiva.dismaps.common.toEnum
import com.bydhiva.dismaps.domain.model.Disaster
import com.bydhiva.dismaps.domain.model.DisasterType
import com.bydhiva.dismaps.utils.stringISOToDate
import com.google.android.gms.maps.model.LatLng
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DummyReports {
    private val disasterType = listOf(DisasterType.FLOOD, DisasterType.EARTHQUAKE, DisasterType.WIND, DisasterType.HAZE, DisasterType.FIRE, DisasterType.VOLCANO)

    /**
     * Return list of reports with various disaster type and province code
     *
     * Use current date to simulate live reports
     */
    fun getLiveReports(): List<Disaster> {
        val liveReports = mutableListOf<Disaster>()
        repeat(200) {
            val report = Disaster(
                pKey = "",
                createdAt = Date(),
                source = "",
                status = "",
                disasterType = disasterType[it%6],
                url = null,
                imageUrl = null,
                title = "",
                text = "",
                latLng = LatLng(  0.0, 0.0),
                depth = null,
                provinceCode = DummyProvinces.provinceList[it%DummyProvinces.provinceList.size].code
            )
            liveReports.add(report)
        }
        return liveReports
    }

    /**
     * Return list of reports with various disaster type and province code
     *
     * Use sequence dates from 31 Jul 2023 - 31 Aug 2023
     */
    fun getReportsArchiveReports(): List<Disaster> {
        val reportsArchive = mutableListOf<Disaster>()
        repeat(200) {
            val report = Disaster(
                pKey = "",
                createdAt = getDate(it%31),
                source = "",
                status = "",
                disasterType = disasterType[it%6],
                url = null,
                imageUrl = null,
                title = "",
                text = "",
                latLng = LatLng(  0.0, 0.0),
                depth = null,
                provinceCode = DummyProvinces.provinceList[it%DummyProvinces.provinceList.size].code
            )
            reportsArchive.add(report)
        }
        return reportsArchive
    }

    private fun getDate(dayOfMonth: Int): Date {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        return formatter.parse("2023-08-$dayOfMonth") ?: Date()
    }
}