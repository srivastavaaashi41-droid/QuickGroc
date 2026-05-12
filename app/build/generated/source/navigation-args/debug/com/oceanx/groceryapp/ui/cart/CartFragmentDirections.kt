package com.oceanx.groceryapp.ui.cart

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.oceanx.groceryapp.R

public class CartFragmentDirections private constructor() {
  public companion object {
    public fun actionCartToCheckout(): NavDirections =
        ActionOnlyNavDirections(R.id.action_cart_to_checkout)
  }
}
