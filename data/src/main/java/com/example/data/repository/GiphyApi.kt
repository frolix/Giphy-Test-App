package com.example.data.repository

import com.example.domain.model.GiphyTreadingResponse
import com.example.data.utilData.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyApi {

    @GET("trending")
    suspend fun getTrendingGiphy(
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("limit") limit:Int= Constants.LIMIT,
        @Query("q") query: String?,
        @Query("offset") offset:Int= 0
    ): Response<GiphyTreadingResponse>

}