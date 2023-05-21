package com.example.aiartcreator.data.remote

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("text2image")
    @FormUrlEncoded
    suspend fun getTextToImage(
        @Header("X-RapidAPI-Key") apiKey: String,
        @Field("prompt") prompt: String,
        @Field("guidance") guidance: Int,
        @Field("steps") steps: Int,
        @Field("upscale") upscale: Int,
        @Field("height") height: Int,
        @Field("width") width: Int,
        @Field("sampler") sampler: String,
    ): Response<ResponseBody>
}
