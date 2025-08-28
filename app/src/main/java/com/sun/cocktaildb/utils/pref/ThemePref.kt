package com.sun.cocktaildb.utils.pref

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

private const val PREFS_NAME = "theme_prefs"
private const val KEY_THEME_MODE = "key_theme_mode"

private var sharedPrefs: SharedPreferences? = null

fun initThemePref(context: Context) {
    if (sharedPrefs == null) {
        sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }
}

fun getThemeMode(): Int {
    val prefs = sharedPrefs ?: return AppCompatDelegate.MODE_NIGHT_NO
    return prefs.getInt(KEY_THEME_MODE, AppCompatDelegate.MODE_NIGHT_NO)
}

fun setThemeMode(mode: Int) {
    sharedPrefs?.edit()?.putInt(KEY_THEME_MODE, mode)?.apply()
}


