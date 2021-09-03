package com.soldatov.data.models

import com.google.gson.annotations.SerializedName

data class TaxiInfo(
    @SerializedName("poiList")
    val taxiList: List<ListItemInfo>
)
