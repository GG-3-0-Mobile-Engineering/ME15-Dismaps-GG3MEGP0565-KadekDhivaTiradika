package com.bydhiva.dismaps.utils

import com.bydhiva.dismaps.R
import com.bydhiva.dismaps.domain.model.DisasterType

fun DisasterType.getColorId() = when(this) {
    DisasterType.FLOOD -> R.color.blue_500
    DisasterType.EARTHQUAKE -> R.color.brown_500
    DisasterType.FIRE -> R.color.yellow_500
    DisasterType.HAZE -> R.color.teal_500
    DisasterType.WIND -> R.color.green_500
    DisasterType.VOLCANO -> R.color.red_500
    DisasterType.DEFAULT -> R.color.blue_500
}