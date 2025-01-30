package com.example.challengeandroid.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.challengeandroid.R
import com.example.challengeandroid.databinding.ActivityMainBinding
import com.example.challengeandroid.presentation.ui.plp.PlpViewModel
import com.google.android.material.badge.ExperimentalBadgeUtils
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private var navController: NavController? = null
    private val viewModel: PlpViewModel by viewModels()


    @OptIn(ExperimentalBadgeUtils::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController!!, appBarConfiguration)
        navView.setupWithNavController(navController!!)

        viewModel.categoriesLiveData.observe(this, Observer { data ->
            data
            updateNavigationMenu(navView, data)
        })

        navView.setNavigationItemSelectedListener { menuItem ->
            drawerLayout.closeDrawers()
            viewModel.setCategory(menuItem.title.toString())
            return@setNavigationItemSelectedListener true
        }

        viewModel.getCategories()
        viewModel.loadProducts()
    }


    private fun updateNavigationMenu(navView: NavigationView, menuItems: List<String>) {

        val mutableCategories = menuItems.toMutableList()

        mutableCategories.add(0, "Todos")

        val menu = navView.menu
        menu.clear()

        for (item in mutableCategories) {
            val menuItem = menu.add(Menu.NONE, Menu.NONE, Menu.NONE, item)
            menuItem.setIcon(R.drawable.baseline_ads_click_24)
        }

    }

    @OptIn(ExperimentalBadgeUtils::class)
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_cart -> {
                navController?.navigate(R.id.action_nav_home_to_cartFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}