package com.labouralerts.ui.activity

import com.labouralerts.R
import com.labouralerts.ui.fragment.LoginFragment

class LoginSignUpActivity : BaseActivity() {
    override fun defineLayoutResource(): Int {
        return R.layout.activity_login_signup
    }

    override fun initializeComponent() {
        replaceFragment(R.id.flContainer, LoginFragment(), false)
    }

}