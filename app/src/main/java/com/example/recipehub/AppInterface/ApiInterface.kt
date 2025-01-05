package com.example.recipehub.AppInterface


import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {
    @POST("api/v1/User/login")
    fun login(@Body param:Map<String,String>): Call<ResponseBody>
}