package com.example.challengeandroid.data.remote

import javax.inject.Inject

class ProductServiceImpl @Inject constructor(private val service: ProductService) :
    BaseDataSource() {

     suspend fun getProducts() = getResult { service.getProducts() }

    suspend fun getProductsById(id: Int) = getResult { service.getProductsById(id) }

    suspend fun getCategories() = getResult { service.getCategories() }

    suspend fun getCategoryById(id: String) = getResult { service.getProductByCategory(id) }


}