package com.example.pdfreader.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pdfreader.R
import com.example.pdfreader.databinding.ActivityMainBinding
import com.example.pdfreader.helper.NavigationManager

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        NavigationManager.getInstance().init(this, supportFragmentManager, R.id.fragment_container)
    }
}