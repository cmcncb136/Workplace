package com.heybuddy.safespace.service

import com.heybuddy.safespace.dto.SubscribeInformationDto
import com.heybuddy.safespace.subscribe.SubscribeInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SubscribeInformationService {

    @GET("subscribe_info/find/")
    fun findSubscribeInfo(@Query("id") id: Int): Call<SubscribeInformationDto>
    @POST("subscribe_info/add/")
    fun addSubscribeInfo(@Query("uid") uid: String,
                         @Query("productId") productId: String): Call<Boolean>
    @POST("subscribe_info/findByUid/")
    fun subscribe_infoFindByUid(@Query("uid") uid: String): Call<List<SubscribeInformationDto>>
}