package com.oceanx.groceryapp.data.model

data class Order(
    val orderId: String,
    val date: String,
    val itemCount: Int,
    val itemsEmoji: String,
    val total: Int,
    val status: OrderStatus
)

enum class OrderStatus {
    PLACED, PREPARING, OUT_FOR_DELIVERY, DELIVERED
}
