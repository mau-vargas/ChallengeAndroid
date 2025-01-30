package com.example.challengeandroid.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.challengeandroid.data.local.CartSharedPreferenceImpl
import com.example.challengeandroid.data.remote.ProductService
import com.example.challengeandroid.data.remote.ProductServiceImpl
import com.example.challengeandroid.data.repository.RepositoryProducts
import com.example.challengeandroid.data.repository.RepositoryProductsImpl
import com.example.challengeandroid.domain.usecase.GetCartUserCase
import com.example.challengeandroid.domain.usecase.GetCategoriesByIdUserCase
import com.example.challengeandroid.domain.usecase.GetCategoriesUserCase
import com.example.challengeandroid.domain.usecase.GetProductByIdUserCase
import com.example.challengeandroid.domain.usecase.GetProductsUserCase
import com.example.challengeandroid.domain.usecase.RemoveProductCartUserCase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl("https://fakestoreapi.com/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideProductService(retrofit: Retrofit): ProductService =
        retrofit.create(ProductService::class.java)

    @Provides
    fun provideProductServiceImpl(productService: ProductService) =
        ProductServiceImpl(productService)


    @Provides
    fun providerGetProductsUserCase(repository: RepositoryProducts) =
        GetProductsUserCase(repository)

    @Provides
    fun providerGetProductByIdUserCase(repository: RepositoryProducts) =
        GetProductByIdUserCase(repository)

    @Provides
    fun providerGetCartUserCase(repository: RepositoryProducts) = GetCartUserCase(repository)

    @Provides
    fun providerRemoveProductCartUserCase(repository: RepositoryProducts) =
        RemoveProductCartUserCase(repository)

    @Provides
    fun providerGetCategoriesUserCase(repository: RepositoryProducts) =
        GetCategoriesUserCase(repository)

    @Provides
    fun providerGetCategoriesByIdUserCase(repository: RepositoryProducts) =
        GetCategoriesByIdUserCase(repository)

    @Singleton
    @Provides
    fun provideRepository(
        service: ProductServiceImpl,
        local: CartSharedPreferenceImpl
    ): RepositoryProducts =
        RepositoryProductsImpl(service, local)


    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("super_app", Context.MODE_PRIVATE)
    }

    fun Context.getSharedPreferencesFrom(name: String): SharedPreferences {
        return getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    @Provides
    fun provideSharedPreferencesManager(
        context: Context, gson: Gson
    ): CartSharedPreferenceImpl {
        return CartSharedPreferenceImpl(context, gson)
    }

    @Provides
    fun provideContext(application: Application): Context = application.applicationContext
}