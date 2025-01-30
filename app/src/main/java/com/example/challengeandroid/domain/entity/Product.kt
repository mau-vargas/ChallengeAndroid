package com.example.challengeandroid.domain.entity

import com.example.challengeandroid.presentation.ui.plp.entity.TypeOfProduct

data class Product(
    val id: Int,
    val title: String,
    val description: String? = "",
    val image: String? = "",
    val price: Double,
    var type: TypeOfProduct? = null,
    val category: String,
    val rating: Rating? = null,
    var quantity: Int = 0
)
