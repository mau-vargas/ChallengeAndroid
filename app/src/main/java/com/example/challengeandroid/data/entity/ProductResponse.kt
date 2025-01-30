package com.example.challengeandroid.data.entity

data class ProductResponse(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: RatingResponse
)

data class RatingResponse(
    val rate: Double,
    val count: Int
)