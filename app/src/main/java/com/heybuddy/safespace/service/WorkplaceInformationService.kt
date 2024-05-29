package com.heybuddy.safespace.service

import com.google.android.gms.common.internal.service.Common
import com.heybuddy.safespace.dto.CommonResult
import com.heybuddy.safespace.dto.WorkplaceInformationDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface WorkplaceInformationService {
    @POST("workplace/exist/")
    fun existWorkplace(@Query("uid") uid: String): Call<Boolean>

    @POST("workplace/join/")
    fun joinWorkplace(@Body workplace: WorkplaceInformationDto): Call<CommonResult>

    @POST("workplace/find/")
    fun findWorkplace(@Query("uid") uid: String): Call<WorkplaceInformationDto>

    @GET("workplace/ip_check/")
    fun getPublicIp(): Call<String>

}