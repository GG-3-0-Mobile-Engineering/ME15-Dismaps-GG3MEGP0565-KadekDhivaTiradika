package com.bydhiva.dismaps.utils

import com.bydhiva.dismaps.R
import com.bydhiva.dismaps.base.LibraryModule
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

private fun getProvinceList(): List<Province> = with(LibraryModule.application) {
        val provincesCode = resources.getStringArray(R.array.province_code)
        return resources.getStringArray(R.array.province_name).mapIndexed { index, s ->
            Province(s, provincesCode[index])
        }.toList().sortedBy { it.name }
}