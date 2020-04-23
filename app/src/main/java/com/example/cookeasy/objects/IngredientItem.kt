package com.example.cookeasy.objects

data class IngredientItem (val name: String, val quantity: String) {
    constructor() : this("", "0")
}