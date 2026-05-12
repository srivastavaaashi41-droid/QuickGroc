package com.oceanx.groceryapp.ui.cart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.oceanx.groceryapp.data.db.AppDatabase
import com.oceanx.groceryapp.data.model.CartItem
import com.oceanx.groceryapp.data.model.Product
import com.oceanx.groceryapp.data.repository.CartRepository
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CartRepository
    val cartItems: LiveData<List<CartItem>>
    val totalPrice: LiveData<Int?>
    val totalItemCount: LiveData<Int?>

    init {
        val cartDao = AppDatabase.getDatabase(application).cartDao()
        repository = CartRepository(cartDao)
        cartItems = repository.allCartItems
        totalPrice = repository.totalPrice
        totalItemCount = repository.totalItemCount
    }

    fun addToCart(product: Product) = viewModelScope.launch {
        val cartItem = CartItem(
            productId = product.id,
            name = product.name,
            weight = product.weight,
            price = product.price,
            emoji = product.emoji
        )
        repository.addToCart(cartItem)
    }

    fun decreaseQty(cartItem: CartItem) = viewModelScope.launch {
        repository.removeFromCart(cartItem)
    }

    fun increaseQty(cartItem: CartItem) = viewModelScope.launch {
        repository.addToCart(cartItem)
    }

    fun deleteItem(cartItem: CartItem) = viewModelScope.launch {
        repository.deleteItem(cartItem)
    }

    fun clearCart() = viewModelScope.launch {
        repository.clearCart()
    }

    suspend fun getCartItemForProduct(productId: Int): CartItem? {
        return repository.getCartItem(productId)
    }
}
