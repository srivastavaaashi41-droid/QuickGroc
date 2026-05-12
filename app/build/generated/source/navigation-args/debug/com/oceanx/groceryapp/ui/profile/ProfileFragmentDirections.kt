package com.oceanx.groceryapp.ui.profile

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.oceanx.groceryapp.R

public class ProfileFragmentDirections private constructor() {
  public companion object {
    public fun actionProfileToOrders(): NavDirections =
        ActionOnlyNavDirections(R.id.action_profile_to_orders)

    public fun actionProfileToLogin(): NavDirections =
        ActionOnlyNavDirections(R.id.action_profile_to_login)
  }
}
