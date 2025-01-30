package com.example.challengeandroid.domain.usecase

import com.example.challengeandroid.data.repository.RepositoryProducts
import com.example.challengeandroid.domain.entity.Product
import javax.inject.Inject


class GetCartUserCase @Inject constructor(private val repository: RepositoryProducts) {
    fun execute(): List<Product> {
        return repository.getLocalProducts()
    }
}