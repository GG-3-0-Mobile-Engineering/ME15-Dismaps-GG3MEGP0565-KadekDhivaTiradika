package com.bydhiva.dismaps.domain.model

import java.util.Date

data class ReportsFilter(
    val disasterType: DisasterType? = null,
    val startEndDate: Pair<Date, Date>? = null
)
