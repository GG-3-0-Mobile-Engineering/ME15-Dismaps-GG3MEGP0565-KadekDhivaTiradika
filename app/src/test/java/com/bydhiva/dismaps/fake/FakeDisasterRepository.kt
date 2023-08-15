package com.bydhiva.dismaps.fake

import com.bydhiva.dismaps.common.toEnum
import com.bydhiva.dismaps.data.repository.DisasterRepository
import com.bydhiva.dismaps.domain.model.Disaster
import com.bydhiva.dismaps.dummy.DummyReports
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FakeDisasterRepository : DisasterRepository {
    private val dummyReports = DummyReports()
    override suspend fun getReports(disaster: String?, provinceCode: String?): List<Disaster> {
        return dummyReports.getLiveReports().filter {
            if (disaster != null && provinceCode != null) {
                return@filter it.disasterType == disaster.toEnum() && it.provinceCode == provinceCode
            } else if (disaster != null) {
                return@filter it.disasterType == disaster.toEnum()
            } else if (provinceCode != null){
                return@filter it.provinceCode == provinceCode
            }
            return@filter true
        }
    }

    override suspend fun getReportsArchive(
        start: String,
        end: String,
        disaster: String?,
        provinceCode: String?
    ): List<Disaster> {
        val filteredReports = dummyReports.getReportsArchiveReports().filter {
            if (disaster != null && provinceCode != null) {
                return@filter it.disasterType == disaster.toEnum() && it.provinceCode == provinceCode
            } else if (disaster != null) {
                return@filter it.disasterType == disaster.toEnum()
            } else if (provinceCode != null){
                return@filter it.provinceCode == provinceCode
            }
            return@filter true
        }
        return filteredReports.filter {
            (it.createdAt?.after(start.stringISOToDate()) ?: false) &&
                    (it.createdAt?.before(end.stringISOToDate()) ?: false)
        }
    }

    private fun String.stringISOToDate(): Date? {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        return formatter.parse(this)
    }
}