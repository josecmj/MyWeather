package com.josecmj.myweatherappschool.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInitializer {
    private val retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun repoService() = retrofit.create(GitHubService::class.java)


    private val weatherBuilder =
        Retrofit.Builder()
            .baseUrl("http://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun weatherService() = weatherBuilder.create(WeatherService::class.java)


}