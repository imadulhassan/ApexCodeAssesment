package com.apex.codeassesment.data.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Id(
  val name: String? = null,
  val value: String? = null
): Parcelable