package com.heybuddy.safespace.service

import com.heybuddy.safespace.dto.WorkplaceInformationDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface WorkplaceInformationService {
    @POST("workplace/exist/")
    fun existWorkplace(@Query("uid") uid: String): Call<Boolean>

    @POST("workplace/join/")
    fun joinWorkplace(@Body workplace: WorkplaceInformationDto): Call<Boolean>

    @POST("workplace/find/")
    fun findWorkplace(@Query("uid") uid: String): Call<WorkplaceInformationDto>

}