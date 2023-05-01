package com.architectcoders.aacboard.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.architectcoders.aacboard.R
import com.architectcoders.aacboard.databinding.ActivityMainNavHostBinding

class MainNavHostActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainNavHostBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        setSupportActionBar(binding.toolbar)
        val navController = (
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                as NavHostFragment
            ).navController
        val config = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, config)
    }
}