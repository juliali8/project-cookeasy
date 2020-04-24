package com.example.cookeasy.Network

import androidx.lifecycle.MutableLiveData
import com.example.cookeasy.Data.DataIngredients
import com.example.cookeasy.Data.DataRecipe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response

class RecipeRepository {
    //get the instance of retrofit
    private val service = ApiClient.makeRetrofitService()

    //searches for recipe based on string value
    fun getRecipeBySearch(resBody : MutableLiveData<DataRecipe>, param:String) {
        //set the coroutine on a background thread
        CoroutineScope(Dispatchers.IO).launch {
            var response: Response<DataRecipe> = service.getRecipeBySearchQuery(param)

            //when the coroutine finishes
            withContext(Dispatchers.Main){
                try{
                    //success case
                    if(response.isSuccessful){
                        //println(response.body()?.size.toString() + " is the size")
                        resBody.value = response.body()
                        println("success")
                        println(response)

                    } else{
                        //response error
                        println("HTTP error")
                    }
                }catch (e: HttpException) {
                    //http exception
                    println("HTTP Exception")
                } catch (e: Throwable) {
                    //error
                    println("Error")
                }
            }
        }
    }
    //searches for recipe ingredients based on ID
    fun getIngredientsByID(resBody : MutableLiveData<DataIngredients>, id:Int) {
        //set the coroutine on a background thread
        CoroutineScope(Dispatchers.IO).launch {
            var response: Response<DataIngredients> = service.getIngredientsByID(id)

            //when the coroutine finishes
            withContext(Dispatchers.Main){
                try{
                    //success case
                    if(response.isSuccessful){
                        //println(response.body()?.size.toString() + " is the size")
                        resBody.value = response.body()
                        println("success")
                        println(response)

                    } else{
                        //response error
                        println("HTTP error")
                    }
                }catch (e: HttpException) {
                    //http exception
                    println("HTTP Exception")
                } catch (e: Throwable) {
                    //error
                    println("Error")
                }
            }
        }
    }
}