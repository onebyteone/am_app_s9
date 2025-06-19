package com.example.app_s9

import android.os.Bundle
import android.util.Base64
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AboutActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferencesHelper = SharedPreferencesHelper(this)
        val themeMode = sharedPreferencesHelper.getInt(
            SharedPreferencesHelper.KEY_THEME_MODE,
            AppCompatDelegate.MODE_NIGHT_NO
        )
        AppCompatDelegate.setDefaultNightMode(themeMode)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_about)
        supportActionBar?.title = getString(R.string.title_about)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.aboutRoot)) { v, insets ->
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

        val developerNameEncoded = getString(R.string.developer_name_encoded)
        val developerNickEncoded = getString(R.string.developer_nick_encoded)
        val developerName = String(Base64.decode(developerNameEncoded, Base64.DEFAULT))
        val developerNick = String(Base64.decode(developerNickEncoded, Base64.DEFAULT))

        findViewById<TextView>(R.id.textViewDeveloper).text = getString(R.string.about_developer, developerName)
        findViewById<TextView>(R.id.textViewCopyright).text = getString(R.string.about_copyright, developerNick)
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
}
