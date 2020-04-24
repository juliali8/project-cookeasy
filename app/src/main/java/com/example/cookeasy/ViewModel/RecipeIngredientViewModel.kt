package com.example.cookeasyapi.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.cookeasy.Data.DataIngredients
import com.example.cookeasy.Network.RecipeRepository

class RecipeIngredientViewModel (application: Application): AndroidViewModel(application){
    var ingredientList: MutableLiveData<DataIngredients> = MutableLiveData()
    var ingredientRepository: RecipeRepository = RecipeRepository()

    //Request to search for recipe
    fun getRecipeIngredient(id:Int){
        ingredientRepository.getIngredientsByID(ingredientList,id)
    }

}