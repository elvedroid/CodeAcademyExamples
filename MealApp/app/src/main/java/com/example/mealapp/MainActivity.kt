package com.example.mealapp

import android.os.Bundle
import android.view.Menu
import com.example.mealapp.ui.base.BaseActivity
import com.example.mealapp.ui.base.BaseFragment
import com.example.mealapp.ui.explore.ExploreFragment
import com.example.mealapp.ui.favorites.FavoritesFragment
import com.example.mealapp.ui.home.HomeFragment
import com.example.mealapp.ui.more.MoreFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        bottom_bar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.explore -> replaceFragment(ExploreFragment())
                R.id.favorites -> replaceFragment(FavoritesFragment())
                R.id.more -> replaceFragment(MoreFragment())
                else -> false
            }
        }
        bottom_bar.selectedItemId = R.id.home
    }

    private fun replaceFragment(fragment: BaseFragment): Boolean {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment)
            .commit()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }
}
