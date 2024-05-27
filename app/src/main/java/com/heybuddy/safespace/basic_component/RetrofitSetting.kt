package com.heybuddy.safespace.basic_component

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitSetting {
    companion object{
        val URL = "http://10.0.2.2:8080/"

        fun getRetrofit() : Retrofit{
            return Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}