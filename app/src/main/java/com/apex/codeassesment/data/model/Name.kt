package com.apex.codeassesment.data.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Name(
  val title: String? = null,
  val first: String? = null,
  val last: String? = null
): Parcelable