package com.oceanx.groceryapp.ui.login

import android.os.Bundle
import androidx.navigation.NavDirections
import com.oceanx.groceryapp.R
import kotlin.Int
import kotlin.String

public class LoginFragmentDirections private constructor() {
  private data class ActionLoginToOtp(
    public val mobile: String,
  ) : NavDirections {
    public override val actionId: Int = R.id.action_login_to_otp

    public override val arguments: Bundle
      get() {
        val result = Bundle()
        result.putString("mobile", this.mobile)
        return result
      }
  }

  public companion object {
    public fun actionLoginToOtp(mobile: String): NavDirections = ActionLoginToOtp(mobile)
  }
}
