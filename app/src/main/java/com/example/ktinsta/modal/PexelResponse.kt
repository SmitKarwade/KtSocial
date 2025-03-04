package com.example.ktinsta.modal

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PexelResponse {
    @SerializedName("page")
    @Expose
    var page: Int? = null

    @SerializedName("per_page")
    @Expose
    var perPage: Int? = null

    @kotlin.jvm.JvmField
    @SerializedName("photos")
    @Expose
    var photos: List<Photo>? = null

    @SerializedName("total_results")
    @Expose
    var totalResults: Int? = null

    @SerializedName("next_page")
    @Expose
    var nextPage: String? = null
}
