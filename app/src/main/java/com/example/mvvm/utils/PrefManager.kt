package com.example.mvvm.utils

import android.content.Context
import android.content.SharedPreferences


class PrefManager(_context: Context) {

    var pref: SharedPreferences =
        _context.getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE)

    var accessToken: String
        get() = pref.getString(Constants.ACCESS_TOKEN, "")!!
        set(value) = pref.edit().putString(Constants.ACCESS_TOKEN, value).apply()


    fun clearAll() {
        pref.edit().clear().apply()
    }
}