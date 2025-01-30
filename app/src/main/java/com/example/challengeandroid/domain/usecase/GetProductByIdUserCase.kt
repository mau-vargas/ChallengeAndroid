package com.example.challengeandroid.domain.usecase

import com.example.challengeandroid.data.repository.RepositoryProducts
import com.example.challengeandroid.domain.entity.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetProductByIdUserCase @Inject constructor(private val repository: RepositoryProducts) {
    fun execute(id: Int): Flow<Product> {
        val response = flow {
            emit(
                repository.getProductsById(id)
            )
        }
        return response
    }
}