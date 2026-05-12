package com.oceanx.groceryapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.oceanx.groceryapp.data.model.CartItem

@Dao
interface CartDao {

    @Query("SELECT * FROM cart_items")
    fun getAllCartItems(): LiveData<List<CartItem>>

    @Query("SELECT * FROM cart_items WHERE productId = :productId")
    suspend fun getCartItem(productId: Int): CartItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(cartItem: CartItem)

    @Delete
    suspend fun delete(cartItem: CartItem)

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()

    @Query("SELECT SUM(price * quantity) FROM cart_items")
    fun getTotalPrice(): LiveData<Int?>

    @Query("SELECT SUM(quantity) FROM cart_items")
    fun getTotalItemCount(): LiveData<Int?>
}
