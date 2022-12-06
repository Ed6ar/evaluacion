package com.edgar.core

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.edgar.data.modules.RETROFIT
import com.edgar.data.modules.VIEW_MODELS
import com.edgar.evaluacion.databinding.ActivityMainBinding
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        startKoin {
            // Enable logs
            androidLogger()
            // Add modules
            modules(VIEW_MODELS, RETROFIT)
        }

        setContentView(binding.root)
    }

}