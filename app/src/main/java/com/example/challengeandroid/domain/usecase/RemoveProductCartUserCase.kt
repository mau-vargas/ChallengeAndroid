package com.example.challengeandroid.domain.usecase

import com.example.challengeandroid.data.repository.RepositoryProducts
import com.example.challengeandroid.domain.entity.Product
import javax.inject.Inject

class RemoveProductCartUserCase @Inject constructor(private val repository: RepositoryProducts) {
    fun execute(param: List<Product>?) {
        repository.removeToCart(param)
    }
}