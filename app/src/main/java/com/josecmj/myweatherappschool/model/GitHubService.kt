package com.josecmj.myweatherappschool.model

import com.josecmj.myweatherappschool.model.repo.Repo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubService {

    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String?): Call<List<Repo>>

}