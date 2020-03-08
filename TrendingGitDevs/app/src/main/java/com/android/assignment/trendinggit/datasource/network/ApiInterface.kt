package com.android.assignment.trendinggit.datasource.network

import com.android.assignment.trendinggit.datasource.roomdb.entity.TrendingDevEntity
import com.android.assignment.trendinggit.datasource.roomdb.entity.TrendingRepoEntity
import retrofit2.http.GET

interface ApiInterface {

    @GET("repositories")
    suspend fun getAllRepos(): List<TrendingRepoEntity>

    @GET("developers")
    suspend fun getAllDevs(): List<TrendingDevEntity>
}