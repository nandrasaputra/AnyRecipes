package com.endiar.anyrecipes

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.endiar.anyrecipes.utils.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            setupBottomNavigationView()
        }

        if (getString(R.string.SPOONACULAR_API_KEY).isEmpty()) {
            Toast.makeText(this, "Missing Spoonacular API key, please add to api.properties as SPOONACULAR_API_KEY=\"YOUR_API_KEY", Toast.LENGTH_LONG).show()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationView()
    }

    private fun setupBottomNavigationView() {
        val navGraphIds = listOf(R.navigation.navigation_home,R.navigation.navigation_fridge, R.navigation.navigation_favorite, R.navigation.navigation_account)

        val controller = main_activity_bottom_nav_view.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.main_activity_nav_host,
            intent = intent
        )

        controller.observe(this) {
            addOnDestinationChangedListener(it)
        }
    }

    private fun showBottomNavBar(show: Boolean) {
        if (show) {
            main_activity_bottom_nav_view.visibility = View.VISIBLE
        } else {
            main_activity_bottom_nav_view.visibility = View.GONE
        }
    }

    private fun addOnDestinationChangedListener(navController: NavController) {
        navController.removeOnDestinationChangedListener(this)
        navController.addOnDestinationChangedListener(this)
    }

    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        when(destination.id) {
            R.id.resultByIngredients, R.id.detailFragment, R.id.detailFragmentInFridge, R.id.detailFragmentInFavorite -> {
                showBottomNavBar(false)
            }
            else -> {
                showBottomNavBar(true)
            }
        }
    }
}