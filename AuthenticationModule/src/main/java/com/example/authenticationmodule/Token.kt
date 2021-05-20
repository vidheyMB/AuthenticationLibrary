package com.example.authenticationmodule

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Token(
    @Json(name = ".expires")
    val expires: String?=null,
    @Json(name = ".issued")
    val issued: String?=null,
    @Json(name = "access_token")
    val access_token: String?=null,
    @Json(name = "expires_in")
    val expires_in: String?=null,
    @Json(name = "token_type")
    val token_type: String?=null,
    @Json(name = "userName")
    val userName: String?=null
)