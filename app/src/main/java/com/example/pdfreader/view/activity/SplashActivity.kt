package com.example.pdfreader.view.activity

import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.pdfreader.databinding.ActivityMainBinding
import com.example.pdfreader.databinding.ActivitySplashBinding
import com.example.pdfreader.task.LoadPdfFileTask
import com.example.pdfreader.view.viewmodel.AppViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler()
    }
}