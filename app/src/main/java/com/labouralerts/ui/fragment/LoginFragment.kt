package com.labouralerts.ui.fragment

import android.content.Intent
import android.view.View
import com.labouralerts.R
import com.labouralerts.ui.activity.HomeActivity
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragments() {
    override fun defineLayoutResource(): Int {
        return R.layout.fragment_login
    }

    override fun initializeComponent(view: View) {
        fragment_login_btnLogin.setOnClickListener(this)
        fragment_login_tvForgotPassword.setOnClickListener(this)
        fragment_login_tvGoToSignUp.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        super.onClick(view)
        val id = view.id

        when (id) {

            R.id.fragment_login_btnLogin -> {
                goToHomeActivity()
            }

            R.id.fragment_login_tvForgotPassword -> {
                addFragment(R.id.flContainer, this, ForgotPasswordFragment(), false)
            }

            R.id.fragment_login_tvGoToSignUp -> {
                addFragment(R.id.flContainer, this, SignUpFragment(), false)
            }
        }
    }

    /**
     * This method is to redirect user to home screen
     */
    private fun goToHomeActivity() {
        val intent = Intent(activity, HomeActivity::class.java);
        startActivity(intent)
        activity!!.finish()
    }

}