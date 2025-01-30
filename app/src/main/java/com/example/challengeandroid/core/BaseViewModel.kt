package com.example.challengeandroid.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challengeandroid.domain.entity.Product
import com.example.challengeandroid.domain.usecase.AddToCartUseCase
import kotlinx.coroutines.launch

abstract class BaseViewModel() : ViewModel() {

}