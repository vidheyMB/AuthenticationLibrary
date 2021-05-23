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
        clients.addInterceptor(interceptor)

        val client: OkHttpClient = clients.build()

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

                    val token_data = Moshi.Builder()
                            .add(KotlinJsonAdapterFactory())
                            .build()
                            .adapter(Token::class.java)
                            .fromJson(response.body?.source()?.buffer)

                    PreferenceHelperToken.setTokenDetails(context, token_data!!)

                    Log.d("TAG", "onResponse: ${token_data.access_token}")

                    preferenceTokenCallBack.tokenDetails(token_data)

                }
            }
        })

    }

}