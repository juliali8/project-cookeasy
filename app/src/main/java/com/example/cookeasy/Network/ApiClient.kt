package com.example.cookeasy.Network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Headers

object ApiClient {

    const val BASE_URL = "https://api.spoonacular.com/"
    fun makeRetrofitService(): RecipeInterface {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(RecipeInterface::class.java)

    }
}