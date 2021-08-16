package com.example.myapplication

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
import com.example.myapplication.databinding.ActivityDialogs1Binding

class Dialogs1 : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityDialogs1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDialogs1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarDialogs1.toolbar)

        binding.appBarDialogs1.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        val textView: TextView = MenuItemCompat.getActionView(navView.menu.findItem(R.id.nav_gallery)) as TextView


        val params = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)

        params.setMargins(0, 20, 0, 0)
        textView.layoutParams = params


        textView.text = "18"
        textView.gravity = Gravity.CENTER_VERTICAL
        textView.foregroundGravity = Gravity.CENTER_VERTICAL

        textView.background = getDrawable(R.drawable.gradient_bg)
        textView.setTextColor(getColor(R.color.white))
        textView.setPadding(10, 10, 10, 10)

        
        val fav: TextView = MenuItemCompat.getActionView(navView.menu.findItem(R.id.nav_fav)) as TextView
        fav.text = "128"
        fav.gravity = Gravity.CENTER_VERTICAL

        val navController = findNavController(R.id.nav_host_fragment_content_dialogs1)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_cart, R.id.nav_fav
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.dialogs1, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_dialogs1)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}