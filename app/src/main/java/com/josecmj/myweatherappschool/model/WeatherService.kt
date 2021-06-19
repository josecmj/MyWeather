package com.josecmj.myweatherappschool.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//        baseURL                |   action    | params
//http://api.openweathermap.org/data/2.5/find?q=recife&units=metric&appid=5fde54966e3e1c8a80e436245bdf9672

interface WeatherService {

    @GET("data/2.5/find")
    fun queryWeather(
        @Query("q") query: String?,
        @Query("appid") appId: String?,
        @Query("units") unit: String?
    ): Call<QueryResult>
}