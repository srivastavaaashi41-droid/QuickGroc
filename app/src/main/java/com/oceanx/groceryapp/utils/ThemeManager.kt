package com.oceanx.groceryapp.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

object ThemeManager {

    private const val PREF_NAME = "theme_pref"
    private const val KEY_DARK_MODE = "dark_mode"

    fun applyTheme(context: Context) {
        val isDark = getPrefs(context).getBoolean(KEY_DARK_MODE, false)
        setDarkMode(isDark)
    }

    fun toggleDarkMode(context: Context): Boolean {
        val prefs = getPrefs(context)
        val newValue = !prefs.getBoolean(KEY_DARK_MODE, false)
        prefs.edit().putBoolean(KEY_DARK_MODE, newValue).apply()
        setDarkMode(newValue)
        return newValue
    }

    fun isDarkMode(context: Context): Boolean =
        getPrefs(context).getBoolean(KEY_DARK_MODE, false)

    private fun setDarkMode(enabled: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (enabled) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    private fun getPrefs(context: Context): SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
}
