package com.example.challengeandroid.data.local

import com.example.challengeandroid.data.entity.ProductLocal
import com.example.challengeandroid.domain.entity.Product

interface CartSharedPreference {
    fun addProduct(product: List<Product>?)
    fun deleteProduct(product: List<Product>?)
    fun deleteAllProduct()
    fun getProducts():List<ProductLocal>
}