package com.example.challengeandroid.domain.usecase

import com.example.challengeandroid.data.repository.RepositoryProducts
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCategoriesUserCase @Inject constructor(private val repository: RepositoryProducts) {
    fun execute(): Flow<List<String>> {
        val response = flow {
            emit(
                repository.getCategories()
            )
        }
        return response
    }
}