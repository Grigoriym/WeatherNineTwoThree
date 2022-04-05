package com.grappim.weatherninetwothree.ui.root

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.grappim.weatherninetwothree.R
import com.grappim.weatherninetwothree.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewBinding()
    }

    private fun initViewBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}