package com.apex.codeassesment.data.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Login(
  val uuid: String? = null,
  val username: String? = null,
  val password: String? = null,
  val salt: String? = null,
  val md5: String? = null,
  val sha1: String? = null,
  val sha256: String? = null
): Parcelable