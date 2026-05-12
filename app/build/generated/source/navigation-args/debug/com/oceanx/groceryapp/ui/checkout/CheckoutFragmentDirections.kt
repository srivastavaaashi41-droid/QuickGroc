package com.oceanx.groceryapp.ui.checkout

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.oceanx.groceryapp.R

public class CheckoutFragmentDirections private constructor() {
  public companion object {
    public fun actionCheckoutToSuccess(): NavDirections =
        ActionOnlyNavDirections(R.id.action_checkout_to_success)
  }
}
