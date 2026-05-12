package com.oceanx.groceryapp.ui.ordersuccess

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.oceanx.groceryapp.R

public class OrderSuccessFragmentDirections private constructor() {
  public companion object {
    public fun actionSuccessToHome(): NavDirections =
        ActionOnlyNavDirections(R.id.action_success_to_home)
  }
}
