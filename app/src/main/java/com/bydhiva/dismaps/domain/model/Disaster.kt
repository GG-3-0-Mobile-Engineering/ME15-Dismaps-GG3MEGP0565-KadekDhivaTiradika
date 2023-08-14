package com.bydhiva.dismaps.domain.model

import com.google.android.gms.maps.model.LatLng
import java.util.Date

data class Disaster(
    val pKey: String,
    val createdAt: Date?,
    val source: String,
    val status: String,
    val disasterType: DisasterType,
    val url: String?,
    val imageUrl: String?,
    val title: String,
    val text: String,
    val latLng: LatLng,
    val depth: Int?,
    val provinceCode: String
)
