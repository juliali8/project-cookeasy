package com.example.cookeasy.Network

import com.example.cookeasy.Data.DataRecipe

import retrofit2.http.*

interface RecipeInterface {
    //Search by parameters

    @GET("recipes/search?apiKey=6eaa5f8381a34866833a7a9d0fc1d599")

    suspend fun getRecipeBySearchQuery(@Query("query") query: String)
            : retrofit2.Response<DataRecipe>
}
