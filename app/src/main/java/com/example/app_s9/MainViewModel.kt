package com.example.app_s9

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel

class MainViewModel(private val prefs: SharedPreferencesHelper) : ViewModel() {

    fun incrementOpenCount(): Int {
        val count = prefs.getInt(SharedPreferencesHelper.KEY_OPEN_COUNT, 0) + 1
        prefs.saveInt(SharedPreferencesHelper.KEY_OPEN_COUNT, count)
        return count
    }

    fun getOpenCount(): Int = prefs.getInt(SharedPreferencesHelper.KEY_OPEN_COUNT, 0)

    fun resetOpenCount() {
        prefs.saveInt(SharedPreferencesHelper.KEY_OPEN_COUNT, 0)
    }

    fun saveUserProfile(profile: UserProfile) {
        prefs.saveString(SharedPreferencesHelper.KEY_PROFILE_NAME, profile.name)
        prefs.saveInt(SharedPreferencesHelper.KEY_PROFILE_AGE, profile.age)
        prefs.saveString(SharedPreferencesHelper.KEY_PROFILE_EMAIL, profile.email)
    }

    fun loadUserProfile(): UserProfile? {
        val name = prefs.getString(SharedPreferencesHelper.KEY_PROFILE_NAME, "")
        val email = prefs.getString(SharedPreferencesHelper.KEY_PROFILE_EMAIL, "")
        val age = prefs.getInt(SharedPreferencesHelper.KEY_PROFILE_AGE, -1)
        return if (name.isNotEmpty() && email.isNotEmpty() && age >= 0) {
            UserProfile(name, age, email)
        } else {
            null
        }
    }

    fun getThemeMode(): Int {
        return prefs.getInt(
            SharedPreferencesHelper.KEY_THEME_MODE,
            AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    fun saveThemeMode(mode: Int) {
        prefs.saveInt(SharedPreferencesHelper.KEY_THEME_MODE, mode)
    }
}
