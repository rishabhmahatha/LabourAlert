package com.labouralerts.ui.fragment

import android.view.View
import com.labouralerts.R
import kotlinx.android.synthetic.main.fragment_forgot_password.*

class ForgotPasswordFragment : BaseFragments(){
    override fun defineLayoutResource(): Int {
        return R.layout.fragment_forgot_password
    }

    override fun initializeComponent(view: View) {
        fragment_forgot_password_tvGoToSignIn.setOnClickListener(this)
        fragment_forgot_password_btnSend.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        super.onClick(view)
        val id = view.id

        when (id) {


            R.id.fragment_forgot_password_tvGoToSignIn -> {
               activity!!.onBackPressed()
            }

            R.id.fragment_forgot_password_btnSend -> {
                activity!!.onBackPressed()
            }
        }
    }

}