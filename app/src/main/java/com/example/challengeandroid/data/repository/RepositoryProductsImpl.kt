package com.example.challengeandroid.data.repository

import com.example.challengeandroid.data.local.CartSharedPreference
import com.example.challengeandroid.data.mapper.toDomain
import com.example.challengeandroid.data.remote.ProductServiceImpl
import com.example.challengeandroid.domain.entity.Product
import javax.inject.Inject

class RepositoryProductsImpl @Inject constructor(
    private val productService: ProductServiceImpl,
    private val localData: CartSharedPreference
) : RepositoryProducts {
    override suspend fun getProducts(): List<Product> {
        val result = productService.getProducts()
        val arrayAux = mutableListOf<Product>()

        result.data?.forEach {
            arrayAux.add(it.toDomain())
        }
        return arrayAux
    }

    override suspend fun getProductsById(id: Int): Product {
        val result = productService.getProductsById(id)
        return result.data?.toDomain()!!
    }

    override suspend fun getCategories(): List<String>{

        return productService.getCategories().data!!
    }

    override suspend fun getCategoriesById(category: String): List<Product> {

        val result = productService.getCategoryById(category)
        val arrayAux = mutableListOf<Product>()
        result.data?.forEach {
            arrayAux.add(it.toDomain())
        }
        return arrayAux
    }

    override fun addToCart(product: List<Product>) {
        localData.addProduct(product)
    }

    override fun getLocalProducts(): List<Product> {
        val list = mutableListOf<Product>()
        localData.getProducts().forEach {
            list.add(
                Product(
                    id = it.id,
                    title = it.title,
                    price = it.price,
                    category = it.category,
                    image = it.image,
                    quantity = it.quantity
                )
            )
        }
        return list
    }

    override fun removeToCart(product: List<Product>?) {
        localData.deleteProduct(product)
    }
}