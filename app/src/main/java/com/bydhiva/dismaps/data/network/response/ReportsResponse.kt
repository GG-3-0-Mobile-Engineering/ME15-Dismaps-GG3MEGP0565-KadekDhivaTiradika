package com.bydhiva.dismaps.data.network.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ReportsResponse(

	@field:SerializedName("result")
	val result: Result,

	@field:SerializedName("statusCode")
	val statusCode: Int
)

@Keep
data class Tags(

	@field:SerializedName("instance_region_code")
	val instanceRegionCode: String,

	@field:SerializedName("local_area_id")
	val localAreaId: String? = null
)


@Keep
data class Output(

	@field:SerializedName("geometries")
	val geometries: List<GeometriesItem?>? = null,

	@field:SerializedName("type")
	val type: String? = null
)


@Keep
data class GeometriesItem(

	@field:SerializedName("coordinates")
	val coordinates: List<Double?>? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("properties")
	val properties: Properties? = null
)


@Keep
data class Result(

	@field:SerializedName("objects")
	val objects: Objects? = null,

	@field:SerializedName("bbox")
	val bBox: List<Double?>? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("arcs")
	val arcs: List<Any?>? = null
)


@Keep
data class Properties(

	@field:SerializedName("report_data")
	val reportData: ReportData? = null,

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("disaster_type")
	val disasterType: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("pkey")
	val pKey: String? = null,

	@field:SerializedName("source")
	val source: String? = null,

	@field:SerializedName("text")
	val text: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("tags")
	val tags: Tags? = null
)


@Keep
data class ReportData(
	@field:SerializedName("report_type")
	val type: String? = null,

	@field:SerializedName("flood_depth")
	val depth: Int? = null,
)


@Keep
data class Objects(
	@field:SerializedName("output")
	val output: Output? = null
)
