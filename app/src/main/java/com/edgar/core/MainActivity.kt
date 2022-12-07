package com.edgar.core

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.edgar.data.UserData
import com.edgar.data.UserDataHolder
import com.edgar.data.modules.RETROFIT
import com.edgar.data.modules.VIEW_MODELS
import com.edgar.evaluacion.R
import com.edgar.evaluacion.databinding.ActivityMainBinding
import com.edgar.ui.viewModels.MainViewModel
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainVideModel by viewModel<MainViewModel>()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        startKoin {
            // Enable logs
            androidLogger(Level.DEBUG)
            androidContext(applicationContext)
            // Add modules
            modules(VIEW_MODELS, RETROFIT)
        }

        navController = setNavController()

        listeners()
        validateAppState()

        setContentView(binding.root)
    }

    private fun listeners() {
        mainVideModel.loading.observe(this) {
            if (it) {
                binding.apply {
                    mainLoader.root.visibility = View.VISIBLE
                    navHostFragment.visibility = View.INVISIBLE
                }
            } else {
                binding.apply {
                    navHostFragment.visibility = View.VISIBLE
                    mainLoader.root.visibility = View.INVISIBLE
                }
            }
        }

        mainVideModel.navigate.observe(this) {
            if(it != null) {
                finish()
                UserDataHolder.setData(Gson().fromJson(it, UserData::class.java))
                navController.navigate(R.id.userActivity)
            }
        }
    }

    private fun validateAppState() {
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE)
        with(sharedPref.getString(this.getString(R.string.userData), null)) {
            if (!this.isNullOrEmpty()) {
                mainVideModel.navigateToUserActivity(this)
            }
            mainVideModel.setLoadingState(false)
        }
    }

    private fun setNavController(): NavController {
        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment
        return navHostFragment.navController
    }

    override fun onStop() {
        super.onStop()
        stopKoin()
    }
}