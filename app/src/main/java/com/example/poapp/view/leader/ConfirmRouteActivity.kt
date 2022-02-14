package com.example.poapp.view.leader

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.poapp.R
import com.example.poapp.view.ImplementedElsewhereFragment
import com.example.poapp.view.NotImplementedFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class ConfirmRouteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_route)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.nav_view_leader)
        bottomNavigationView.selectedItemId = R.id.navigation_confirm
        bottomNavigationView.setOnItemSelectedListener { item ->
            var selectedFragment: Fragment? = null

            when (item.itemId) {
                R.id.navigation_ebook -> selectedFragment = NotImplementedFragment()
                R.id.navigation_route -> selectedFragment = ImplementedElsewhereFragment()
                R.id.navigation_mountain_passes -> selectedFragment = NotImplementedFragment()
                R.id.navigation_confirm -> selectedFragment = ConfirmRouteListFragment()
            }

            if (selectedFragment != null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment_activity_confirm, selectedFragment).commit()
            }
            true
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_activity_confirm, ConfirmRouteListFragment()).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.settings_menu, menu)
        return true
    }

}