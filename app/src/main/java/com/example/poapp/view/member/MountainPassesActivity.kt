package com.example.poapp.view.member

import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.poapp.R
import com.example.poapp.view.NotImplementedFragment
import com.example.poapp.viewModel.MountainPassOfficialViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MountainPassesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mountain_passes)
        val mViewModel: MountainPassOfficialViewModel by viewModels()

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.nav_view_employee)
        bottomNavigationView.selectedItemId = R.id.navigation_passes
        bottomNavigationView.setOnItemSelectedListener { item ->
            var selectedFragment: Fragment? = null

            when (item.itemId) {
                R.id.navigation_passes -> selectedFragment = MountainPassesListFragment()
                R.id.navigation_account -> selectedFragment = NotImplementedFragment()
            }

            if (selectedFragment != null) {
                mViewModel.resetMountainPass()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.nav_host_fragment_activity_mountain_passes_list, selectedFragment)
                    .commit()
            }
            true
        }

        supportFragmentManager.beginTransaction()
            .replace(
                R.id.nav_host_fragment_activity_mountain_passes_list,
                MountainPassesListFragment()
            ).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.settings_menu, menu)
        return true
    }

}