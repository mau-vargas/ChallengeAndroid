package com.example.challengeandroid.domain.usecase

import com.example.challengeandroid.data.repository.RepositoryProducts
import com.example.challengeandroid.domain.entity.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProductsUserCase @Inject constructor(private val repository: RepositoryProducts) {
    fun execute(): Flow<List<Product>> {
        val response = flow {
            emit(
                repository.getProducts()
            )
        }
        return response
    }
}