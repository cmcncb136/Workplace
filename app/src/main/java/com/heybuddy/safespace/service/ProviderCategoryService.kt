package com.heybuddy.safespace.service

import com.heybuddy.safespace.dto.ProviderCategoryDto
import com.heybuddy.safespace.dto.SubscribeInformationDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ProviderCategoryService {
    @GET("provider_category/findAll/")
    fun findProviderCategory(): Call<List<ProviderCategoryDto>>

}