package com.example.app_s9

import android.os.Bundle
import com.google.android.material.button.MaterialButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import android.content.Intent
import android.view.Menu
import android.view.MenuItem

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    private lateinit var viewModel: MainViewModel

    private lateinit var buttonResetCounter: MaterialButton
    private lateinit var textViewOpenCount: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferencesHelper = SharedPreferencesHelper(this)
        val themeMode = sharedPreferencesHelper.getInt(
            SharedPreferencesHelper.KEY_THEME_MODE,
            AppCompatDelegate.MODE_NIGHT_NO
        )
        AppCompatDelegate.setDefaultNightMode(themeMode)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(sharedPreferencesHelper) as T
            }
        })[MainViewModel::class.java]

        // Inicializar vistas
        initViews()

        // Configurar listeners
        setupListeners()

        updateOpenCount()
    }

    private fun initViews() {
        buttonResetCounter = findViewById(R.id.buttonResetCounter)
        textViewOpenCount = findViewById(R.id.textViewOpenCount)
    }

    private fun setupListeners() {
        buttonResetCounter.setOnClickListener { resetCounter() }
    }

    private fun updateOpenCount() {
        val count = viewModel.incrementOpenCount()
        textViewOpenCount.text = count.toString()
    }


    private fun resetCounter() {
        viewModel.resetOpenCount()
        textViewOpenCount.text = "0"
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_profile -> {
                startActivity(Intent(this, ProfileActivity::class.java))
                true
            }
            R.id.action_preferences -> {
                startActivity(Intent(this, PreferencesActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}