package com.labouralerts.ui.fragment

import android.content.Intent
import android.view.View
import com.labouralerts.R
import com.labouralerts.ui.activity.HomeActivity
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpFragment:BaseFragments(){
    override fun defineLayoutResource(): Int {
        return R.layout.fragment_sign_up
    }

    override fun initializeComponent(view: View) {
        fragment_signup_btnSignUp.setOnClickListener(this)
        fragment_signup_tvGoToSignIn.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        super.onClick(view)
        val id = view.id

        when (id) {

            R.id.fragment_signup_btnSignUp -> {
                goToHomeActivity()
            }

            R.id.fragment_signup_tvGoToSignIn -> {
               activity!!.onBackPressed()
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