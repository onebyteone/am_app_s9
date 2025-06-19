package com.example.app_s9

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class ProfileActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper

    private lateinit var editTextName: TextInputEditText
    private lateinit var editTextAge: TextInputEditText
    private lateinit var editTextEmail: TextInputEditText
    private lateinit var buttonSaveProfile: MaterialButton
    private lateinit var buttonLoadProfile: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferencesHelper = SharedPreferencesHelper(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.profileRoot)) { v, insets ->
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

        initViews()
        setupListeners()
    }

    private fun initViews() {
        editTextName = findViewById(R.id.editTextName)
        editTextAge = findViewById(R.id.editTextAge)
        editTextEmail = findViewById(R.id.editTextEmail)
        buttonSaveProfile = findViewById(R.id.buttonSaveProfile)
        buttonLoadProfile = findViewById(R.id.buttonLoadProfile)
    }

    private fun setupListeners() {
        buttonSaveProfile.setOnClickListener { saveProfile() }
        buttonLoadProfile.setOnClickListener { loadProfile() }
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
}
