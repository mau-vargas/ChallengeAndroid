package com.example.challengeandroid.data.remote

import com.example.challengeandroid.data.entity.ProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {

    @GET("products")
    suspend fun getProducts(): Response<List<ProductResponse>>

    @GET("products/{id}")
    suspend fun getProductsById(@Path("id") id: Int): Response<ProductResponse>

    @GET("products/categories")
    suspend fun getCategories(): Response<List<String>>

    @GET("products/category/{category}")
    suspend fun getProductByCategory(@Path("category") category: String): Response<List<ProductResponse>>
}