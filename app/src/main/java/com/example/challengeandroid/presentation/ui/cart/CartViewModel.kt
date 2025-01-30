package com.example.challengeandroid.presentation.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.challengeandroid.core.BaseViewModel
import com.example.challengeandroid.domain.entity.Product
import com.example.challengeandroid.domain.usecase.AddToCartUseCase
import com.example.challengeandroid.domain.usecase.GetCartUserCase
import com.example.challengeandroid.domain.usecase.RemoveProductCartUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartUserCase: GetCartUserCase,
    private val removeProductCartUserCase: RemoveProductCartUserCase,
    private val addToCartUseCase: AddToCartUseCase
) : BaseViewModel() {

    private val _cartLiveData = MutableLiveData<List<Product>>()
    val cartLiveData: LiveData<List<Product>> get() = _cartLiveData

    private val _totalAmount = MutableLiveData<String>()
    val totalAmount: LiveData<String> get() = _totalAmount

    fun getCartInformation() {
        _cartLiveData.value = getCartUserCase.execute()
    }

    fun addToCart(productId: Int) {
        val products = _cartLiveData.value?.filter { it.id == productId }
        addToCartUseCase.execute(products)
    }

    fun getTotalAmount(data: List<Product>) {
        val totalPrice = data.sumOf { it.price * it.quantity}
        _totalAmount.value ="Total Price: $totalPrice"
    }

    fun removeProduct(productId: Int){
        val products = _cartLiveData.value?.filter { it.id == productId }
        removeProductCartUserCase.execute(products)
    }

}
