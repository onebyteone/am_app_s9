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
    private lateinit var buttonClearFields: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferencesHelper = SharedPreferencesHelper(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        supportActionBar?.title = getString(R.string.title_profile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
        loadProfile()
        setupListeners()
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initViews() {
        editTextName = findViewById(R.id.editTextName)
        editTextAge = findViewById(R.id.editTextAge)
        editTextEmail = findViewById(R.id.editTextEmail)
        buttonSaveProfile = findViewById(R.id.buttonSaveProfile)
        buttonClearFields = findViewById(R.id.buttonClearFields)
    }

    private fun setupListeners() {
        buttonSaveProfile.setOnClickListener { saveProfile() }
        buttonClearFields.setOnClickListener { clearFields() }
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
        if (profile != null) {
            editTextName.setText(profile.name)
            editTextAge.setText(profile.age.toString())
            editTextEmail.setText(profile.email)
        }
    }

    private fun clearFields() {
        editTextName.setText("")
        editTextAge.setText("")
        editTextEmail.setText("")
    }
}
