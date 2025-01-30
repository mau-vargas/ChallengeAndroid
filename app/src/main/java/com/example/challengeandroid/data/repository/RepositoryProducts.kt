package com.example.challengeandroid.data.repository

import com.example.challengeandroid.domain.entity.Product

interface RepositoryProducts {
    suspend fun getProducts(): List<Product>
    suspend fun getProductsById(id:Int): Product
    suspend fun getCategories() : List<String>
    suspend fun getCategoriesById(category:String) : List<Product>
    fun addToCart(product: List<Product>?)
    fun getLocalProducts(): List<Product>
    fun removeToCart(product: List<Product>?)

}