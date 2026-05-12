package com.oceanx.groceryapp.data.model

data class Product(
    val id: Int,
    val name: String,
    val weight: String,
    val price: Int,
    val originalPrice: Int = price,
    val emoji: String,
    val category: String,
    val discountPercent: Int = 0
)
