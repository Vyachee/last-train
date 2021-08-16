package com.example.podcaster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.podcaster.databinding.ActivityMainScreenBinding
import com.example.podcaster.fragments.DiscoverFragment
import com.example.podcaster.fragments.LibraryFragment
import com.example.podcaster.fragments.RecordFragment
import com.google.android.material.navigation.NavigationBarView

class MainScreen : AppCompatActivity() {

    lateinit var binding: ActivityMainScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setOnItemSelectedListener(object: NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when(item.itemId) {
                    R.id.discover -> replaceFragment(DiscoverFragment())
                    R.id.record -> replaceFragment(RecordFragment())
                    R.id.library -> replaceFragment(LibraryFragment())
                }
                return false
            }
        })
    }

    fun replaceFragment(fragment: Fragment) {

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView, fragment)
        transaction.commit()
        transaction.addToBackStack(null)
    }
}