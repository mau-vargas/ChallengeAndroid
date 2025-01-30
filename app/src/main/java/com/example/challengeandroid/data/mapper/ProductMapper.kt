package com.example.challengeandroid.data.mapper

import com.example.challengeandroid.data.entity.ProductResponse
import com.example.challengeandroid.data.entity.RatingResponse
import com.example.challengeandroid.domain.entity.Product
import com.example.challengeandroid.domain.entity.Rating
import com.example.challengeandroid.presentation.ui.plp.entity.TypeOfProduct

fun ProductResponse.toDomain(): Product {
    return Product(
        id = this.id,
        title = this.title,
        price = this.price,
        description = this.description,
        image = this.image,
        type = TypeOfProduct.Normal(),
        category = this.category,
        rating =  this.rating.toDomain()
    )
}

fun RatingResponse.toDomain(): Rating {
    return Rating(
        rate = this.rate,
        count = this.count,
        coverage = this.rate * this.count
    )
}