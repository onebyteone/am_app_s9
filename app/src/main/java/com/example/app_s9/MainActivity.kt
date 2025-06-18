package com.example.app_s9

import android.os.Bundle
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    private lateinit var viewModel: MainViewModel

    private lateinit var editTextName: TextInputEditText
    private lateinit var editTextAge: TextInputEditText
    private lateinit var editTextEmail: TextInputEditText
    private lateinit var buttonSaveProfile: MaterialButton
    private lateinit var buttonLoadProfile: MaterialButton
    private lateinit var buttonResetCounter: MaterialButton
    private lateinit var textViewOpenCount: TextView
    private lateinit var switchDarkMode: SwitchCompat
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
        editTextName = findViewById(R.id.editTextName)
        editTextAge = findViewById(R.id.editTextAge)
        editTextEmail = findViewById(R.id.editTextEmail)
        buttonSaveProfile = findViewById(R.id.buttonSaveProfile)
        buttonLoadProfile = findViewById(R.id.buttonLoadProfile)
        buttonResetCounter = findViewById(R.id.buttonResetCounter)
        textViewOpenCount = findViewById(R.id.textViewOpenCount)
        switchDarkMode = findViewById(R.id.switchDarkMode)
        switchDarkMode.isChecked =
            viewModel.getThemeMode() == AppCompatDelegate.MODE_NIGHT_YES
    }

    private fun setupListeners() {
        buttonSaveProfile.setOnClickListener { saveProfile() }
        buttonLoadProfile.setOnClickListener { loadProfile() }
        buttonResetCounter.setOnClickListener { resetCounter() }
        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            val mode = if (isChecked) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
            viewModel.saveThemeMode(mode)
            AppCompatDelegate.setDefaultNightMode(mode)
        }
    }

    private fun updateOpenCount() {
        val count = viewModel.incrementOpenCount()
        textViewOpenCount.text = "Veces abierta: $count"
    }

    private fun saveProfile() {
        val name = editTextName.text.toString().trim()
        val ageText = editTextAge.text.toString().trim()
        val email = editTextEmail.text.toString().trim()

        val age = ageText.toIntOrNull()

        if (name.isEmpty() || age == null || email.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.saveUserProfile(UserProfile(name, age, email))
        Toast.makeText(this, "Perfil guardado", Toast.LENGTH_SHORT).show()
        editTextName.setText("")
        editTextAge.setText("")
        editTextEmail.setText("")
    }

    private fun loadProfile() {
        val profile = viewModel.loadUserProfile()
        if (profile == null) {
            Toast.makeText(this, "No hay perfil guardado", Toast.LENGTH_SHORT).show()
        } else {
            editTextName.setText(profile.name)
            editTextAge.setText(profile.age.toString())
            editTextEmail.setText(profile.email)
            Toast.makeText(this, "Perfil cargado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun resetCounter() {
        viewModel.resetOpenCount()
        textViewOpenCount.text = "Veces abierta: 0"
    }
}