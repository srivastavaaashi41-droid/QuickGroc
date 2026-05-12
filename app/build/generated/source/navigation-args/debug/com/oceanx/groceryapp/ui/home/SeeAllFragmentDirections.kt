package com.oceanx.groceryapp.ui.home

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.oceanx.groceryapp.R

public class SeeAllFragmentDirections private constructor() {
  public companion object {
    public fun actionSeeAllToCart(): NavDirections =
        ActionOnlyNavDirections(R.id.action_see_all_to_cart)
  }
}
