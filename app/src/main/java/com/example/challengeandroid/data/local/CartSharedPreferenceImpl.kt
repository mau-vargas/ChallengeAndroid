package com.example.challengeandroid.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.challengeandroid.data.entity.ProductLocal
import com.example.challengeandroid.di.AppModule.getSharedPreferencesFrom
import com.example.challengeandroid.domain.entity.Product
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class CartSharedPreferenceImpl @Inject constructor(
    private val context: Context,
    private val gson: Gson
) : CartSharedPreference {
    private var sharedPreferences: SharedPreferences? = null

    private val PREF_NAME = "super_app"
    private val PREF_PRODUCTS = "LIST_OF_PRODUCTS"

    private fun getPreferences(context: Context, name: String = PREF_NAME): SharedPreferences {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferencesFrom(name)
        }
        return sharedPreferences!!
    }

    override fun addProduct(productsList: List<Product>?) {
        val product = productsList?.first()

        product?.let {
            val localProduct = ProductLocal(
                id = product.id,
                title = product.title,
                price = product.price,
                category = product.category,
                image = product.image,
                quantity = 1
            )

            val sharedPreferences = getPreferences(context)

            val productsListJson = sharedPreferences.getString(PREF_PRODUCTS, null)

            val currentProducts = if (!productsListJson.isNullOrEmpty()) {
                gson.fromJson<List<ProductLocal>>(
                    productsListJson,
                    object : TypeToken<List<ProductLocal>>() {}.type
                )
                    .toMutableList()
            } else {
                mutableListOf<ProductLocal>()
            }


            val existingProduct = currentProducts.find { it.id == localProduct.id }

            if (existingProduct != null) {
                existingProduct.quantity += 1
            } else {
                currentProducts.add(localProduct)
            }

            val updatedProductsListJson = gson.toJson(currentProducts)

            sharedPreferences.edit().apply {
                putString(PREF_PRODUCTS, updatedProductsListJson)
                apply()
            }
        }
    }


    override fun deleteProduct(productList: List<Product>?) {
        val sharedPreferences = getPreferences(context)
        val productsListJson = sharedPreferences.getString(PREF_PRODUCTS, null)

        val product = productList?.first()

        product?.let {
            val localProduct = ProductLocal(
                id = product.id,
                title = product.title,
                price = product.price,
                category = product.category,
                image = product.image,
                quantity = 1
            )


            val currentProducts = if (!productsListJson.isNullOrEmpty()) {
                gson.fromJson<List<ProductLocal>>(
                    productsListJson,
                    object : TypeToken<List<ProductLocal>>() {}.type
                )
                    .toMutableList()
            } else {
                mutableListOf<ProductLocal>()
            }


            val existingProduct = currentProducts.find { it.id == localProduct.id }

            if (existingProduct != null) {
                existingProduct.quantity -= 1
                if (existingProduct.quantity == 0) {
                    currentProducts.remove(existingProduct)
                }
            }

            val updatedProductsListJson = gson.toJson(currentProducts)

            sharedPreferences.edit().apply {
                putString(PREF_PRODUCTS, updatedProductsListJson)
                apply()
            }
        }
    }

    override fun deleteAllProduct() {
        TODO("Not yet implemented")
    }

    override fun getProducts(): List<ProductLocal> {
        val sharedPreferences = getPreferences(context)

        val productsListJson = sharedPreferences.getString(PREF_PRODUCTS, null)

        return if (!productsListJson.isNullOrEmpty()) {
            try {
                val type = object : TypeToken<List<ProductLocal>>() {}.type
                gson.fromJson<List<ProductLocal>>(productsListJson, type)
            } catch (e: JsonSyntaxException) {
                emptyList()
            }
        } else {
            emptyList()
        }
    }


}