package com.example.authenticationlibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.authenticationmodule.AuthAPICall
import com.example.authenticationmodule.PreferenceHelperToken
import com.example.authenticationmodule.PreferenceTokenCallBack
import com.example.authenticationmodule.Token

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val url = "http://demoserv2.arokiait.com/WAVIN/token"
        val requestBody = "grant_type=password&UserName=Wavin&Password=HFjf\$fgjhksdfl#g"

       /* AuthAPICall.run(this, url, requestBody, object : PreferenceTokenCallBack {
            override suspend fun tokenDetails(token: Token?) {
                Log.d("TAG", "tokenDetails: ${PreferenceHelperToken.getTokenDetails(this@MainActivity)?.access_token}")
            }
        })*/

    }
}