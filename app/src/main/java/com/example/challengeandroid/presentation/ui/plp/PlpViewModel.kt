package com.example.challengeandroid.presentation.ui.plp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challengeandroid.domain.entity.Product
import com.example.challengeandroid.domain.usecase.AddToCartUseCase
import com.example.challengeandroid.domain.usecase.GetCategoriesByIdUserCase
import com.example.challengeandroid.domain.usecase.GetCategoriesUserCase
import com.example.challengeandroid.domain.usecase.GetProductsUserCase
import com.example.challengeandroid.presentation.ui.plp.entity.TypeOfProduct
import com.example.challengeandroid.util.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlpViewModel @Inject constructor(
    private val getProductsUserCase: GetProductsUserCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val getCategoriesUserCase: GetCategoriesUserCase,
    private val getCategoriesByIdUserCase: GetCategoriesByIdUserCase
) : ViewModel()  {

    private val _productsLiveData = MutableLiveData<UIState<List<Product>>>()
    val productsLiveData: LiveData<UIState<List<Product>>> get() = _productsLiveData

    private val _categoriesLiveData = MutableLiveData<List<String>>()
    val categoriesLiveData: LiveData<List<String>> get() = _categoriesLiveData


    fun addToCart(productId: Int) {
        val filteredProducts = _productsLiveData.value
        if (filteredProducts is UIState.Success) {
            val products = filteredProducts.data.filter { it.id == productId }
            products.map {
                it.copy(type = null)
            }
            products.let {
                viewModelScope.launch {
                    addToCartUseCase.execute(products)
                }
            }
        }
    }

    fun loadProducts() {
        viewModelScope.launch {
            getProductsUserCase.execute()
                .collect { products ->
                    setProducts(products)
                }
        }
    }

    fun getCategories() {
        viewModelScope.launch {
            getCategoriesUserCase.execute()
                .collect { categories ->
                    _categoriesLiveData.value = categories
                }
        }

    }

    private fun getProductsWithBestCoverage(products: List<Product>): List<Product> {
        val maxCoverage = products.maxOfOrNull { it.rating?.coverage ?: 0.0 } ?: return emptyList()
        return products.filter { (it.rating?.coverage ?: 0.0) == maxCoverage }

    }

    fun setCategory(category: String) {
        _productsLiveData.value = UIState.Loading

        if (category != "Todos") {
            viewModelScope.launch {
                getCategoriesByIdUserCase.execute(category)
                    .collect { products ->
                        setProducts(products)
                    }
            }
        } else {
            loadProducts()
        }
    }

    private fun setProducts(products: List<Product>) {
        val bestProduct = getProductsWithBestCoverage(products)
        val response = products.map {
            if (it.id == bestProduct.first().id)
                it.copy(type = TypeOfProduct.Featured())
            else
                it
        }
        val updatedList =
            response.sortedBy { if (it.id == bestProduct.first().id) -1 else it.id }

        _productsLiveData.value = UIState.Success(updatedList)
    }
}