package com.example.challengeandroid.presentation.ui.plp.entity

sealed class TypeOfProduct {
    class Normal : TypeOfProduct()
    class Featured : TypeOfProduct()
}