package com.example.poapp.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.poapp.databinding.ActivityMainBinding
import com.example.poapp.view.leader.ConfirmRouteActivity
import com.example.poapp.view.member.MountainPassesActivity
import com.example.poapp.view.tourist.route.SaveRouteActivity
import com.example.poapp.viewModel.RouteViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val model: RouteViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.saveRouteAddProof.setOnClickListener {
            val intent = Intent(this, SaveRouteActivity::class.java)
            startActivity(intent)
        }

        binding.mountainPass.setOnClickListener {
            val intent = Intent(this, MountainPassesActivity::class.java)
            startActivity(intent)
        }

        binding.confirmRoute.setOnClickListener {
            val intent = Intent(this, ConfirmRouteActivity::class.java)
            startActivity(intent)
        }

        model.cleanInvalid()
    }
}