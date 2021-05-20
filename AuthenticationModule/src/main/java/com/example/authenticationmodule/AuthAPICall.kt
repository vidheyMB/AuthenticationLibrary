package com.example.authenticationmodule

import android.content.Context
import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException

interface PreferenceTokenCallBack{
    fun tokenDetails(token: Token?)
}

object AuthAPICall {

    private val clients: OkHttpClient.Builder = OkHttpClient.Builder()

    fun run(context: Context, url: String, requestBody: String, preferenceTokenCallBack: PreferenceTokenCallBack) {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BASIC
        interceptor.level = HttpLoggingInterceptor.Level.HEADERS
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client: OkHttpClient = clients.addInterceptor(interceptor).build()

        val mediaType = "application/x-www-form-urlencoded".toMediaTypeOrNull()
        val body: RequestBody = requestBody.toRequestBody(mediaType)

        val request: Request = Request.Builder()
            .url(url)
            .post(body)
            .addHeader("content-type", "application/x-www-form-urlencoded")
            .addHeader("cache-control", "no-cache")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("TAG", "onFailure: ${e.message}")

                preferenceTokenCallBack.tokenDetails(null)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val string  = "{\"access_token\":\"5ZR8CxXDdsDJlBRpoWMCl3Tk9n-BOqUuovXL4VIzVk1A7RQXC1A-luODxZzN2c-h7PBgl9VdMo_uTsar_EZXk7Ojg86-kXYMJgmWMAAm7D8b1pcis3KIQpfMKEIQwWDmdodK6rn0RuC61G1ClLbvsOVb5m6Pj--_-52hf8OR99YCZqnbOY8F83TiGldPpFmY-sB14hdV6cRHseiEq2pHHKHwttCEVvemsRLTiQgfRoo\",\"token_type\":\"bearer\",\"expires_in\":14399,\"userName\":\"partnerrewards\",\".issued\":\"Thu, 20 May 2021 16:22:09 GMT\",\".expires\":\"Thu, 20 May 2021 20:22:09 GMT\"}"

                    val token_data = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                            .adapter(Token::class.java)
                            .fromJson(string)
//                            .fromJson(response.body.toString())

                    PreferenceHelperToken.setTokenDetails(context, token_data!!)

                    Log.d("TAG", "onResponse: ${token_data.access_token}")

                    preferenceTokenCallBack.tokenDetails(token_data)
                }
            }
        })

    }

}