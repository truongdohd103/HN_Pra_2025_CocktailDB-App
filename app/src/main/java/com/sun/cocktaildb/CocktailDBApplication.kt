package com.sun.cocktaildb

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.sun.cocktaildb.utils.pref.getThemeMode
import com.sun.cocktaildb.utils.pref.initThemePref

class CocktailDBApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        // Initialize theme preferences with application context
        initThemePref(this)

        // Initialize theme from preferences
        val savedThemeMode = getThemeMode()
        // Force apply theme immediately
        AppCompatDelegate.setDefaultNightMode(savedThemeMode)
        
        // Also set the theme for the application context
        setTheme(com.sun.cocktaildb.R.style.Theme_CocktailDB)
    }
}
