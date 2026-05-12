package com.oceanx.groceryapp.ui.login

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.String
import kotlin.jvm.JvmStatic

public data class OtpFragmentArgs(
  public val mobile: String,
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putString("mobile", this.mobile)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("mobile", this.mobile)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): OtpFragmentArgs {
      bundle.setClassLoader(OtpFragmentArgs::class.java.classLoader)
      val __mobile : String?
      if (bundle.containsKey("mobile")) {
        __mobile = bundle.getString("mobile")
        if (__mobile == null) {
          throw IllegalArgumentException("Argument \"mobile\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"mobile\" is missing and does not have an android:defaultValue")
      }
      return OtpFragmentArgs(__mobile)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): OtpFragmentArgs {
      val __mobile : String?
      if (savedStateHandle.contains("mobile")) {
        __mobile = savedStateHandle["mobile"]
        if (__mobile == null) {
          throw IllegalArgumentException("Argument \"mobile\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"mobile\" is missing and does not have an android:defaultValue")
      }
      return OtpFragmentArgs(__mobile)
    }
  }
}
