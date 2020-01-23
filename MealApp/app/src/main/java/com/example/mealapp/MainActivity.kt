package com.example.mealapp

import android.os.Bundle
import android.view.Menu
import android.view.View
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
                R.id.home -> replaceFragment(HomeFragment(), R.id.home_container)
                R.id.explore -> replaceFragment(ExploreFragment(), R.id.explore_container)
                R.id.favorites -> replaceFragment(FavoritesFragment(), R.id.favorites_container)
                R.id.more -> replaceFragment(MoreFragment(), R.id.more_container)
                else -> false
            }
        }
        bottom_bar.selectedItemId = R.id.home
    }

    private fun replaceFragment(fragment: BaseFragment, container: Int): Boolean {
        var fragmentToReplace = fragment
        var topFragment = supportFragmentManager.findFragmentById(container)
        if (topFragment is BaseFragment) {
            fragmentToReplace = topFragment
        }

        supportFragmentManager.beginTransaction()
            .replace(container, fragmentToReplace)
            .commit()

        favorites_container.visibility = View.GONE
        explore_container.visibility = View.GONE
        home_container.visibility = View.GONE
        more_container.visibility = View.GONE
        findViewById<View>(container).visibility = View.VISIBLE

        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }
}
