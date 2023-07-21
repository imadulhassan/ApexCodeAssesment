package com.apex.codeassesment.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class BaseResponseModel(
    @SerializedName("results") var results: ArrayList<User>? = null,

    ) : Parcelable