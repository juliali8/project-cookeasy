package com.example.cookeasy.objects

data class GroceryItem (val name: String, val quantity: String) {
    constructor() : this("", "0")
}