package com.oceanx.groceryapp.ui.login

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.oceanx.groceryapp.R

public class OtpFragmentDirections private constructor() {
  public companion object {
    public fun actionOtpToHome(): NavDirections = ActionOnlyNavDirections(R.id.action_otp_to_home)
  }
}
