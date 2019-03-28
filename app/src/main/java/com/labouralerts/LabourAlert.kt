package com.labouralerts

import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import android.support.v7.app.AppCompatDelegate

class LabourAlert : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        MultiDex.install(this)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    companion object {

        @get:Synchronized
        var instance: LabourAlert? = null
            private set
        val TAG = LabourAlert::class.java.simpleName
    }

}