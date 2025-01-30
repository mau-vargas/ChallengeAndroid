package com.example.challengeandroid.presentation.ui.pdp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challengeandroid.domain.entity.Product
import com.example.challengeandroid.domain.usecase.AddToCartUseCase
import com.example.challengeandroid.domain.usecase.GetProductByIdUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PdpBottomSheetViewModel @Inject constructor(
    private val getProductByIdUserCase: GetProductByIdUserCase,
    private val addToCartUseCase: AddToCartUseCase
): ViewModel() {

    private val _productsLiveData = MutableLiveData<Product>()
    val productsLiveData: LiveData<Product> get() = _productsLiveData

    private val _cartLiveData = MutableLiveData<List<Product>>()
    val cartLiveData: LiveData<List<Product>> get() = _cartLiveData

    fun getProductById(id:Int){
        viewModelScope.launch {

            getProductByIdUserCase.execute(id)
                .collect { products ->
                    _productsLiveData.value = products
                }
        }
    }

    fun addToCart(productId: Int) {
        val products = _cartLiveData.value?.filter { it.id == productId }
        products?.let {
            addToCartUseCase.execute(products)
        }
    }

}