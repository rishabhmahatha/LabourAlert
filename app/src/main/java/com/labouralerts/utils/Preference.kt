package com.labouralerts.utils

import android.content.Context
import android.content.SharedPreferences
import com.labouralerts.LabourAlert
import com.labouralerts.R


/**
 * Preference class created
 */
class Preference private constructor() {

    private val sharedPreferences: SharedPreferences =
        LabourAlert.instance!!.getSharedPreferences(LabourAlert.instance!!.getString(R.string.app_name), Context.MODE_PRIVATE)


    var isLogin: Boolean
        get() = sharedPreferences.getBoolean(KEY_IS_LOGIN, false)
        set(isLoggedIn) = sharedPreferences.edit().putBoolean(KEY_IS_LOGIN, isLoggedIn).apply()

    object FCMPreference {
        private const val PREFERENCE_FCM_TOKEN = "FCM_TOKEN"
        private const val PREFERENCE_FCM_FILE_NAME = "FCM"

        var fcmToken: String?
            get() {

                val preferences = LabourAlert.instance!!.getSharedPreferences(PREFERENCE_FCM_FILE_NAME, Context.MODE_PRIVATE)
                return preferences.getString(PREFERENCE_FCM_TOKEN, "")
            }
            set(token) {

                val preferences = LabourAlert.instance!!.getSharedPreferences(PREFERENCE_FCM_FILE_NAME, Context.MODE_PRIVATE)

                val editor = preferences.edit()
                editor.putString(PREFERENCE_FCM_TOKEN, token)
                editor.apply()
            }

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
