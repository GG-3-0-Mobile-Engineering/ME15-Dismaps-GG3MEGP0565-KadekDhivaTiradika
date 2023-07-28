package com.bydhiva.dismaps.utils

import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bydhiva.dismaps.R
import com.bydhiva.dismaps.domain.model.DisasterType

fun ImageView.loadImage(url: String?, type: DisasterType) {
    val placeHolder = CircularProgressDrawable(this.context)
    placeHolder.setColorSchemeColors(
        R.color.blue_100,
        R.color.blue_500,
        R.color.blue_100
    )
    placeHolder.centerRadius = 30f
    placeHolder.strokeWidth = 5f
    placeHolder.start()

    Glide.with(this.context)
        .load(url)
        .transform(RoundedCorners(9))
        .placeholder(placeHolder)
        .error(getErrorDrawable(type))
        .into(this)
}

fun ImageView.changeDrawable(id: Int){
    Glide.with(this.context)
        .load(ResourcesCompat.getDrawable(resources, id, null))
        .into(this)
}

fun DisasterType.getDisasterIconId() = when(this) {
    DisasterType.FLOOD -> R.drawable.ic_round_flood_24
    DisasterType.EARTHQUAKE -> R.drawable.ic_round_earthquake_24
    DisasterType.FIRE -> R.drawable.ic_round_fire_24
    DisasterType.HAZE -> R.drawable.ic_round_haze_24
    DisasterType.WIND -> R.drawable.ic_round_wind_24
    DisasterType.VOLCANO -> R.drawable.ic_round_volcano_24
    DisasterType.DEFAULT -> R.drawable.ic_round_flood_24
}

internal fun getErrorDrawable(type: DisasterType) = when(type) {
    DisasterType.FLOOD -> R.drawable.ph_flood
    DisasterType.EARTHQUAKE -> R.drawable.ph_earthquake
    DisasterType.FIRE -> R.drawable.ph_fire
    DisasterType.HAZE -> R.drawable.ph_haze
    DisasterType.WIND -> R.drawable.ph_wind
    DisasterType.VOLCANO -> R.drawable.ph_volcano
    DisasterType.DEFAULT -> R.drawable.ph_flood
}
