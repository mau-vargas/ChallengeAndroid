package com.example.challengeandroid.data.entity



data class ProductLocal(
    val id: Int,
    val title: String,
    val price: Double,
    val category: String,
    val image: String? = "",
    var quantity: Int
)
