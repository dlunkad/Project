package com.example.apprenticeproject.network

import com.example.apprenticeproject.network.responses.HiringResponse
import retrofit2.Response
import retrofit2.http.GET

interface HiringApi {

    @GET("hiring.json")
    suspend fun getHiringJson(): Response<ArrayList<HiringResponse>>
}
