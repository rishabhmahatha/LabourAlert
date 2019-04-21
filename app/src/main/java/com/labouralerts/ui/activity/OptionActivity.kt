package com.labouralerts.ui.activity

import android.content.Intent
import android.view.View
import com.labouralerts.R
import kotlinx.android.synthetic.main.activity_splash.*

class OptionActivity : BaseActivity() {
    override fun defineLayoutResource(): Int {
        return R.layout.activity_splash_new
    }

    override fun initializeComponent() {
        activity_splash_tvLogIn.setOnClickListener(this)
        activity_splash_tvViewResult.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        super.onClick(view)

        when (view.id) {
            R.id.activity_splash_tvLogIn -> {
                val intentLogin = Intent(this, LoginSignUpActivity::class.java)
                startActivity(intentLogin)
                this.finish()
            }

            R.id.activity_splash_tvViewResult -> {
                val intentLogin = Intent(this, HomeActivity::class.java)
                startActivity(intentLogin)
                this.finish()
            }
        }
    }

}