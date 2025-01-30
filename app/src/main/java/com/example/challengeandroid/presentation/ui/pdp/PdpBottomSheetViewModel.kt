package com.example.challengeandroid.presentation.ui.pdp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challengeandroid.domain.entity.Product
import com.example.challengeandroid.domain.usecase.GetProductByIdUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PdpBottomSheetViewModel @Inject constructor(
    private val getProductByIdUserCase: GetProductByIdUserCase
): ViewModel() {

    private val _productsLiveData = MutableLiveData<Product>()
    val productsLiveData: LiveData<Product> get() = _productsLiveData

    fun getProductById(id:Int){
        viewModelScope.launch {

            getProductByIdUserCase.execute(id)
                .collect { products ->
                    _productsLiveData.value = products
                }
        }
    }

}