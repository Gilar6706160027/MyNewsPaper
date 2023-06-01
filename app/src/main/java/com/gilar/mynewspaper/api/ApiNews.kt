package com.gilar.mynewspaper.api

import com.gilar.mynewspaper.model.NewsResponse
import com.gilar.mynewspaper.util.Constants.KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiNews {

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
            @Query("country")
            countryCode:String = "in",
            @Query("page")
            pageCount:Int = 1,
            @Query("apikey")
            key: String = KEY,
            @Query("category")
            category:String? = null
    ):Response<NewsResponse>

    @GET("v2/everything")
    suspend fun searchNews(
            @Query("q")
            query:String ,
            @Query("page")
            pageCount:Int = 1,
            @Query("apikey")
            key: String = KEY
    ):Response<NewsResponse>
}