package com.oceanx.groceryapp.data.repository

import com.oceanx.groceryapp.data.db.CartDao
import com.oceanx.groceryapp.data.model.CartItem

class CartRepository(private val cartDao: CartDao) {

    val allCartItems = cartDao.getAllCartItems()
    val totalPrice = cartDao.getTotalPrice()
    val totalItemCount = cartDao.getTotalItemCount()

    suspend fun addToCart(cartItem: CartItem) {
        val existing = cartDao.getCartItem(cartItem.productId)
        if (existing != null) {
            cartDao.insertOrUpdate(existing.copy(quantity = existing.quantity + 1))
        } else {
            cartDao.insertOrUpdate(cartItem)
        }
    }

    suspend fun removeFromCart(cartItem: CartItem) {
        if (cartItem.quantity > 1) {
            cartDao.insertOrUpdate(cartItem.copy(quantity = cartItem.quantity - 1))
        } else {
            cartDao.delete(cartItem)
        }
    }

    suspend fun deleteItem(cartItem: CartItem) {
        cartDao.delete(cartItem)
    }

    suspend fun getCartItem(productId: Int): CartItem? {
        return cartDao.getCartItem(productId)
    }

    suspend fun clearCart() {
        cartDao.clearCart()
    }
}
