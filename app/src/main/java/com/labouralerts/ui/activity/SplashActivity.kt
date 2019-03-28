package com.labouralerts.ui.activity

import android.os.Handler
import com.labouralerts.R


const val SPLASH_DELAY_TIME: Long = 3000

class SplashActivity : BaseActivity() {

    private lateinit var mDelayHandler: Handler

    private val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
        }
    }

    override fun defineLayoutResource(): Int {
        // Change value
        return R.layout.activity_splash
    }

    override fun initializeComponent() {
        //Initialize the Handler
        mDelayHandler = Handler()

        //Navigate with delay
        mDelayHandler.postDelayed(mRunnable, SPLASH_DELAY_TIME)
    }


    override fun onPause() {
        super.onPause()
        mDelayHandler.removeCallbacks(mRunnable)
    }
}


