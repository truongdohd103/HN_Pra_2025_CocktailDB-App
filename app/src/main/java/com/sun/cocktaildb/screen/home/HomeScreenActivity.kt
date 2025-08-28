package com.sun.cocktaildb.screen.home

import androidx.fragment.app.Fragment
import com.sun.cocktaildb.R
import com.sun.cocktaildb.databinding.ActivityHomeScreenBinding
import com.sun.cocktaildb.screen.favorite.FavoriteFragment
import com.sun.cocktaildb.screen.profile.ProfileFragment
import com.sun.cocktaildb.screen.search.SearchFragment
import com.sun.cocktaildb.utils.base.BaseActivity
import androidx.appcompat.app.AppCompatDelegate
import com.sun.cocktaildb.utils.pref.getThemeMode
import com.sun.cocktaildb.utils.pref.setThemeMode



class HomeScreenActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeScreenBinding

    private lateinit var homeFragment: HomeFragment
    private lateinit var favoritesFragment: FavoriteFragment
    private lateinit var searchFragment: SearchFragment
    private lateinit var profileFragment: ProfileFragment
    private var activeFragment: Fragment? = null

    override fun initView() {
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ensure the theme switch is on top of any overlapping views
        binding.themeSwitch.bringToFront()

        setupFragments()
        setupBottomNavigation()
        setupThemeSwitch()
    }

    private fun setupFragments() {
        val fm = supportFragmentManager

        val existingHome = fm.findFragmentByTag("home") as? HomeFragment
        val existingFavorites = fm.findFragmentByTag("favorites") as? FavoriteFragment
        val existingSearch = fm.findFragmentByTag("search") as? SearchFragment
        val existingProfile = fm.findFragmentByTag("profile") as? ProfileFragment

        if (existingHome != null || existingFavorites != null || existingSearch != null || existingProfile != null) {
            homeFragment = existingHome ?: HomeFragment()
            favoritesFragment = existingFavorites ?: FavoriteFragment()
            searchFragment = existingSearch ?: SearchFragment()
            profileFragment = existingProfile ?: ProfileFragment()

            val fragments = listOfNotNull(existingHome, existingFavorites, existingSearch, existingProfile)
            activeFragment = fragments.firstOrNull { !it.isHidden } ?: existingHome ?: homeFragment

            val tx = fm.beginTransaction()
            fragments.forEach { fragment ->
                if (fragment != activeFragment) tx.hide(fragment) else tx.show(fragment)
            }
            tx.commit()
        } else {
            homeFragment = HomeFragment()
            favoritesFragment = FavoriteFragment()
            searchFragment = SearchFragment()
            profileFragment = ProfileFragment()
            activeFragment = homeFragment
            fm.beginTransaction()
                .add(R.id.fragment_container, profileFragment, "profile").hide(profileFragment)
                .add(R.id.fragment_container, searchFragment, "search").hide(searchFragment)
                .add(R.id.fragment_container, favoritesFragment, "favorites").hide(favoritesFragment)
                .add(R.id.fragment_container, homeFragment, "home")
                .commit()
        }
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    showFragment(homeFragment)
                    true
                }
                R.id.navigation_favorites -> {
                    showFragment(favoritesFragment)
                    true
                }
                R.id.navigation_search -> {
                    showFragment(searchFragment)
                    true
                }
                R.id.navigation_profile -> {
                    showFragment(profileFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun setupThemeSwitch() {
        val isDark = getThemeMode() == AppCompatDelegate.MODE_NIGHT_YES
        binding.themeSwitch.isChecked = isDark

        binding.themeSwitch.setOnCheckedChangeListener { _, checked ->
            val mode = if (checked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            setThemeMode(mode)
            AppCompatDelegate.setDefaultNightMode(mode)
        }
    }

    private fun showFragment(target: Fragment) {
        if (activeFragment === target) return
        val tx = supportFragmentManager.beginTransaction()
        if (!target.isAdded) {
            val tag = when (target) {
                is HomeFragment -> "home"
                is FavoriteFragment -> "favorites"
                is SearchFragment -> "search"
                is ProfileFragment -> "profile"
                else -> target.tag
            }
            tx.add(R.id.fragment_container, target, tag)
        }
        if (activeFragment != null && activeFragment !== target) tx.hide(activeFragment!!)
        tx.show(target).commit()
        activeFragment = target
    }
}
