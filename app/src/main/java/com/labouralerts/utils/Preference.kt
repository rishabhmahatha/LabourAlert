package com.labouralerts.utils

import android.content.Context
import android.content.SharedPreferences
import com.labouralerts.LabourAlert
import com.labouralerts.R


/**
 * Preference class created
 */
class Preference private constructor() {
    val PREF_USER_ID = "USER_ID"
    val PREF_FIRST_NAME = "FIRST_NAME"
    val PREF_LAST_NAME = "LAST_NAME"
    val PREF_SERVICE_ID = "SERVICE_ID"
    val PREF_RENEW_DATE = "RENEW_DATE"
    val PREF_ACCOUNT_TYPE = "ACCOUNT_TYPE"



    private val sharedPreferences: SharedPreferences =
        LabourAlert.instance!!.getSharedPreferences(
            LabourAlert.instance!!.getString(R.string.app_name),
            Context.MODE_PRIVATE
        )


    var isLogin: Boolean
        get() = sharedPreferences.getBoolean(KEY_IS_LOGIN, false)
        set(isLoggedIn) = sharedPreferences.edit().putBoolean(KEY_IS_LOGIN, isLoggedIn).apply()

    fun setData(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getData(key: String, defaultValue: String) {
        sharedPreferences.getString(key, defaultValue)
    }

    fun getStringData(key: String, defaultValue: String): String? {
        return sharedPreferences.getString(key, defaultValue)
    }


    fun getBooleanData(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    fun setBooleanData(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }


    fun getIntData(key: String, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    fun setIntData(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }


    fun clearAllPreferenceData() {
        sharedPreferences.edit().clear().apply()
    }

    companion object {
        private var preference: Preference? = null
        private const val KEY_IS_LOGIN = "KEY_IS_LOGIN"

        val instance: Preference
            get() {

                if (preference == null) {
                    preference = Preference()
                }
                return preference as Preference
            }
    }


}
