package com.oceanx.groceryapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey
    val productId: Int,
    val name: String,
    val weight: String,
    val price: Int,
    val emoji: String,
    var quantity: Int = 1
) {
    val totalPrice: Int get() = price * quantity
}
