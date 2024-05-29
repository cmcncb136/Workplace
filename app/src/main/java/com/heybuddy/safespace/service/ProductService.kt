package com.heybuddy.safespace.service

import com.heybuddy.safespace.dto.ProductDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {
    @GET("product/findAll/")
    fun findAllProduct(): Call<List<ProductDto>>
    @GET("product/find/")
    fun findProduct(@Query("productId") productId: String): Call<ProductDto>
    @GET("product/findProvider/")
    fun findByProviderIdProduct(@Query("providerId") providerId: String): Call<List<ProductDto>>
}