package com.bydhiva.dismaps.common

import com.bydhiva.dismaps.data.network.response.GeometriesItem
import com.bydhiva.dismaps.domain.model.Disaster
import com.bydhiva.dismaps.domain.model.DisasterType
import com.bydhiva.dismaps.utils.stringISOToDate
import com.google.android.gms.maps.model.LatLng

fun GeometriesItem.toDisaster(): Disaster = Disaster(
    pKey = this.properties?.pKey ?: "",
    createdAt = (this.properties?.createdAt ?: "").stringISOToDate(),
    source = this.properties?.source ?: "",
    status = this.properties?.status ?: "",
    disasterType = (this.properties?.disasterType ?: "").toEnum(),
    url = this.properties?.url,
    imageUrl = this.properties?.imageUrl,
    title = this.properties?.title ?: "",
    text = this.properties?.text ?: "",
    latLng = LatLng( this.coordinates?.last() ?: 0.0, this.coordinates?.first() ?: 0.0),
    depth = this.properties?.reportData?.depth
)

internal fun String.toEnum() = when(this) {
    "flood" -> DisasterType.FLOOD
    "earthquake" -> DisasterType.EARTHQUAKE
    "fire" -> DisasterType.FIRE
    "haze" -> DisasterType.HAZE
    "wind" -> DisasterType.WIND
    "volcano" -> DisasterType.VOLCANO
    else -> DisasterType.DEFAULT
}