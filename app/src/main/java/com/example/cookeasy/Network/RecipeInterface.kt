package com.example.cookeasy.Network

import com.example.cookeasy.RecipeList
import com.google.android.gms.common.api.Response
import retrofit2.Response
import retrofit2.http.*

interface RecipeInterface {
    //Search by parameters

    @GET("recipes/search?apiKey=6eaa5f8381a34866833a7a9d0fc1d599")

    suspend fun getRecipeBySearchQuery(@Query("query") query: String)
            : Response<RecipeList>
}