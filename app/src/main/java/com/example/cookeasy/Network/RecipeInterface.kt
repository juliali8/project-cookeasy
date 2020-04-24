package com.example.cookeasy.Network

import com.example.cookeasy.Data.DataIngredients
import com.example.cookeasy.Data.DataRecipe
import retrofit2.Response
import retrofit2.http.*

interface RecipeInterface {
    //Search by parameters

    @GET("recipes/search?apiKey=6eaa5f8381a34866833a7a9d0fc1d599")

    suspend fun getRecipeBySearchQuery(@Query("query") query: String)
            : Response<DataRecipe>

    @GET("recipes/{id}/ingredientWidget.json?apiKey=6eaa5f8381a34866833a7a9d0fc1d599")
    suspend fun getIngredientsByID(@Path("id") id: Int)
            : Response<DataIngredients>
}
