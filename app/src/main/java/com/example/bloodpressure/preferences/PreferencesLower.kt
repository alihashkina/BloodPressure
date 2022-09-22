package com.example.bloodpressure.preferences

import android.content.Context

class PreferencesLower(context: Context){
    val PREFERANCE_NAME_SETTINGS = "settings"
    val PREFERANCE_VALUE_LOWER = "PREFERANCE_VALUE_LOWER"

    val preferences = context.getSharedPreferences(PREFERANCE_NAME_SETTINGS, Context.MODE_PRIVATE)

    fun getValue(): String? {
        return preferences.getString(PREFERANCE_VALUE_LOWER, "80")
    }

    fun setValue(value: String) {
        val editor = preferences.edit()
        editor.putString(PREFERANCE_VALUE_LOWER, value)
        editor.apply()
    }
}