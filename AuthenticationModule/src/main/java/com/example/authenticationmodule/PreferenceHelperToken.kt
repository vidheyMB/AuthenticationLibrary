package com.example.authenticationmodule

import android.content.Context
import com.squareup.moshi.JsonAdapter

import com.squareup.moshi.Moshi


object PreferenceHelperToken {

    interface PreferenceTokenCallBack{
        fun customerDetails(token: Token)
    }

    private const val PreferenceName = "LoyaltyPreferenceHelper_Auth2O"

    fun setBooleanValue(context: Context, key: String, value: Boolean){
        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getBooleanValue(context: Context, key: String): Boolean{
        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(key, false)
    }

    fun setStringValue(context: Context, key: String, value: String){
        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
            sharedPreferences.edit().putString(key, value).apply()
    }

    fun getStringValue(context: Context, key: String): String{
        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, "")!!
    }

    fun setLongValue(context: Context, key: String, value: Long){
        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
            sharedPreferences.edit().putLong(key, value).apply()
    }

    fun getLongValue(context: Context, key: String): Long{
        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
        return sharedPreferences.getLong(key, 0L)
    }

   fun setTokenDetails(context: Context, token: Token){
       val jsonAdapter: JsonAdapter<Token> = jsonAdapter()
       val json = jsonAdapter.toJson(token)

       val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
       sharedPreferences.edit().putString("token", json).apply()
   }

    fun getTokenDetails(context: Context) : Token? {
        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
        val stringValue = sharedPreferences.getString("token", "")

        val jsonAdapter: JsonAdapter<Token> = jsonAdapter()
        return if(!stringValue.isNullOrEmpty()) {
            jsonAdapter.fromJson(stringValue)
        }else{
            null
        }
    }

    // EOF Registration data hold until process get complete

    fun clear(context: Context){
        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
    }

    private fun jsonAdapter(): JsonAdapter<Token> {
        val moshi = Moshi.Builder().build()
        return moshi.adapter(Token::class.java)
    }

}
