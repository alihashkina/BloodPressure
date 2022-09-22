package com.example.bloodpressure.preferences

import android.content.Context

class PreferencesUpper(context: Context){
    val PREFERANCE_NAME_SETTINGS = "settings"
    val PREFERANCE_VALUE_UPPER = "PREFERANCE_VALUE_UPPER"

    val preferences = context.getSharedPreferences(PREFERANCE_NAME_SETTINGS, Context.MODE_PRIVATE)

    fun getValue(): String? {
        return preferences.getString(PREFERANCE_VALUE_UPPER, "120")
    }

    fun setValue(value: String) {
        val editor = preferences.edit()
        editor.putString(PREFERANCE_VALUE_UPPER, value)
        editor.apply()
    }
}