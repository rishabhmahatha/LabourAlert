package com.labouralerts.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.labouralerts.R
import com.labouralerts.utils.Preference

class SplashActivity : BaseActivity() {
    override fun initializeComponent() {

    }

    /*
     * Handler is used to set some delay on this screen
     */
    private var handler: Handler? = null
    private val runnable = Runnable {
        if (Preference.instance.isLogin) {
            goToHomeActivity()
        } else {
            val intent = Intent(this, OptionActivity::class.java);
            startActivity(intent)
            finish()
        }

    }

    override fun defineLayoutResource(): Int {
        return R.layout.activity_splash_screen

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isDuplicateInstance()) {//return if this is duplicate instance of same category and instance
            return
        }

        executeHandler()

    }

    /**
     * This method will prevent multiple instances of an activity when it is launched with different intents
     */
    private fun isDuplicateInstance(): Boolean {
        if (!isTaskRoot) {
            val intent = intent
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN == intent.action) {
                finish()
                return true
            }
        }
        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        if (handler != null) {
            handler!!.removeCallbacks(runnable)
            finish()
        }
    }

    private fun executeHandler() {
        handler = Handler()
        val INTERVAL: Long = 2000
        handler!!.postDelayed(runnable, INTERVAL)
    }


    /**
     * This method is to redirect user to home screen
     */
    private fun goToHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java);
        startActivity(intent)
        this.finish()
    }
}