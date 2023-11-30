package com.example.template.utils

import android.content.Context
import com.example.template.constants.Constants

object Preferences {

    fun savePreference(name: String, value: String, context: Context){
        val sharedPreferences = context.getSharedPreferences(Constants.NAME_PREFERENCES, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(name, value)
        editor.apply()
    }

    fun getPreferenceString(value: String, context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(Constants.NAME_PREFERENCES, Context.MODE_PRIVATE)
        return sharedPreferences.getString(value, null)
    }

    fun deletePreferencias(nombrePreferencias: String, context: Context) {
        val sharedPreferences = context.getSharedPreferences(Constants.NAME_PREFERENCES, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove(nombrePreferencias)
        editor.apply()
    }

}