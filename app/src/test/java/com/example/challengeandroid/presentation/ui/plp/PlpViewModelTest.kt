package com.example.challengeandroid.presentation.ui.plp

import androidx.lifecycle.Observer
import com.example.challengeandroid.domain.entity.Product
import com.example.challengeandroid.domain.entity.Rating
import com.example.challengeandroid.domain.usecase.AddToCartUseCase
import com.example.challengeandroid.domain.usecase.GetCategoriesByIdUserCase
import com.example.challengeandroid.domain.usecase.GetCategoriesUserCase
import com.example.challengeandroid.domain.usecase.GetProductsUserCase
import com.example.challengeandroid.presentation.ui.plp.entity.TypeOfProduct
import com.example.challengeandroid.util.UIState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.test.runTest
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class PlpViewModelTest{
    private lateinit var plpViewModel: PlpViewModel
    private lateinit var getProductsUserCase: GetProductsUserCase
    private lateinit var addToCartUseCase: AddToCartUseCase
    private lateinit var getCategoriesUserCase: GetCategoriesUserCase
    private lateinit var getCategoriesByIdUserCase: GetCategoriesByIdUserCase


    @Before
    fun setUp() {
        getProductsUserCase = mockk()
        addToCartUseCase = mockk()
        getCategoriesUserCase = mockk()
        getCategoriesByIdUserCase = mockk()

        plpViewModel = PlpViewModel(
            getProductsUserCase,
            addToCartUseCase,
            getCategoriesUserCase,
            getCategoriesByIdUserCase
        )
    }


    @Test
    fun `loadProducts should load products successfully`() = runTest {
        // given
        val mockProducts = listOf(
            Product(
                id = 0,
                title = "Product 1",
                description = "description",
                image=  "Image",
                price = 0.0,
                category = "category",
                quantity= 0
            ),
            Product(
                id = 2,
                title = "Product 1",
                description = "description",
                image=  "Image",
                price = 0.0,
                category = "category",
                quantity= 0
            )
        )


        coEvery { getProductsUserCase.execute() } returns flow {
            emit(mockProducts) // Emite los productos
        }


        val observer = mockk<Observer<UIState<List<Product>>>>(relaxed = true)
        plpViewModel.productsLiveData.observeForever(observer)

        // qhen
        plpViewModel.loadProducts()

        // then
        coVerify { observer.onChanged(UIState.Success(mockProducts)) }
    }
}