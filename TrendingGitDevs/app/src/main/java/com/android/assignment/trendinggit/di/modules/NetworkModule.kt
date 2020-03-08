package com.android.assignment.trendinggit.di.modules

import com.android.assignment.trendinggit.R
import com.android.assignment.trendinggit.TrendingGitDevApplication
import com.android.assignment.trendinggit.datasource.network.ApiInterface
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(TrendingGitDevApplication.getContext()?.getString(R.string.app_base_url)!!)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiInterface(retrofit: Retrofit): ApiInterface = retrofit.create(
        ApiInterface::class.java
    )
}