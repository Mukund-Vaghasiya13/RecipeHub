package com.example.recipehub.AppInterface


import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {
    @POST("api/v1/User/login")
    fun login(@Body param:Map<String,String>): Call<ResponseBody>

    @POST("api/v1/User/register")
    fun Signup(@Body param:Map<String,String>): Call<ResponseBody>

    @GET("api/v1/Recipe/list")
    fun ListRecipe(@Query("page") page: Int, @Header("Authorization") token: String ):Call<ResponseBody>

}