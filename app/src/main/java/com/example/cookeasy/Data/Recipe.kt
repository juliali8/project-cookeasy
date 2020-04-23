package com.example.cookeasy.Data

import java.net.URL
import java.time.Duration
import java.util.*

data class Recipe (
    val id: Int,
    val title: String,
    val image: String,
    val servings: Int,
    val readyInMinutes: Int,
    val sourceName: String,
    val healthScore: Int
)