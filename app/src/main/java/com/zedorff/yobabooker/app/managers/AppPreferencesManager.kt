package com.zedorff.yobabooker.app.managers

import android.content.Context
import android.content.SharedPreferences
import com.zedorff.yobabooker.app.di.qualifiers.AppContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferencesManager @Inject constructor(@AppContext context: Context) {
    private val PREFERENCE_NAME: String = "yoba_preferences"
    private val KEY_INIT_DATA: String = "initial_data"

    private val preferences: SharedPreferences

    init {
        preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    fun storeInitialDataWritten() {
        preferences.edit()
                .putBoolean(KEY_INIT_DATA, true)
                .apply()
    }

    fun isInitialDataWritten(): Boolean = preferences.getBoolean(KEY_INIT_DATA, false)
}