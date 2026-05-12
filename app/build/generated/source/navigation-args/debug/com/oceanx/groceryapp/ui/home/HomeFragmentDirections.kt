package com.oceanx.groceryapp.ui.home

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.oceanx.groceryapp.R

public class HomeFragmentDirections private constructor() {
  public companion object {
    public fun actionHomeToCart(): NavDirections = ActionOnlyNavDirections(R.id.action_home_to_cart)

    public fun actionHomeToSeeAll(): NavDirections =
        ActionOnlyNavDirections(R.id.action_home_to_see_all)
  }
}
