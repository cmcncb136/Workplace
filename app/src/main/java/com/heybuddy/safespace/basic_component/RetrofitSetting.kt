package com.heybuddy.safespace.basic_component

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitSetting {
    companion object{
        val URL = "http://10.0.2.2:8080/"

        fun getRetrofit() : Retrofit{
            return Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create(
                    GsonBuilder().setLenient().create() //엄격한 규칙을 좀 더 자유롭게해준다.
                ))
                .build()
        }
    }
}