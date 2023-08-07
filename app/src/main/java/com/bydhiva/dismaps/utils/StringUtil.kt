package com.bydhiva.dismaps.utils

import com.bydhiva.dismaps.R
import com.bydhiva.dismaps.domain.model.DisasterType
import com.bydhiva.dismaps.domain.model.Province

fun DisasterType.getStringId() = when(this) {
    DisasterType.FLOOD -> R.string.flood
    DisasterType.EARTHQUAKE -> R.string.earthquake
    DisasterType.FIRE -> R.string.fire
    DisasterType.HAZE -> R.string.haze
    DisasterType.WIND -> R.string.wind
    DisasterType.VOLCANO -> R.string.volcano
    DisasterType.DEFAULT -> R.string.flood
}

fun String.getProvinceCode() = getProvinceList().find { it.name.equals(this, ignoreCase = true) }?.code

private fun getProvinceList(): List<Province> =
    StringArray.provinceList.sortedBy { it.name }

class StringArray {
    companion object {
        val provinceList = listOf(
            Province("Aceh", "ID-AC"),
            Province("Bali", "ID-BA"),
            Province("Kep Bangka Belitung", "ID-BB"),
            Province("Banten", "ID-BT"),
            Province("Bengkulu", "ID-BE"),
            Province("Jawa Tengah", "ID-JT"),
            Province("Kalimantan Tengah", "ID-KT"),
            Province("Sulawesi Tengah", "ID-ST"),
            Province("Jawa Timur", "ID-JI"),
            Province("Kalimantan Timur", "ID-KI"),
            Province("Nusa Tenggara Timur", "ID-NT"),
            Province("Gorontalo", "ID-GO"),
            Province("DKI Jakarta", "ID-JK"),
            Province("Jambi", "ID-JA"),
            Province("Lampung", "ID-LA"),
            Province("Maluku", "ID-MA"),
            Province("Kalimantan Utara", "ID-KU"),
            Province("Maluku Utara", "ID-MU"),
            Province("Sulawesi Utara", "ID-SA"),
            Province("Sumatera Utara", "ID-SU"),
            Province("Papua", "ID-PA"),
            Province("Riau", "ID-RI"),
            Province("Kepulauan Riau", "ID-KR"),
            Province("Sulawesi Tenggara", "ID-SG"),
            Province("Kalimantan Selatan", "ID-KS"),
            Province("Sulawesi Selatan", "ID-SN"),
            Province("Sumatera Selatan", "ID-SS"),
            Province("DI Yogyakarta", "ID-YO"),
            Province("Jawa Barat", "ID-JB"),
            Province("Kalimantan Barat", "ID-KB"),
            Province("Nusa Tenggara Barat", "ID-NB"),
            Province("Papua Barat", "ID-PB"),
            Province("Sulawesi Barat", "ID-SR"),
            Province("Sumatera Barat", "ID-SB"),
        )
    }
}
