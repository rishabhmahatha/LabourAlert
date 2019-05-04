package com.labouralerts.ui.fragment

import android.app.ProgressDialog
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.google.gson.Gson
import com.labouralerts.R
import com.labouralerts.ui.activity.HomeActivity
import com.labouralerts.ui.model.DataModel
import com.labouralerts.utils.Preference
import com.labouralerts.utils.Utils
import com.labouralerts.webservice.WSUtils
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_sign_up.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
                if (isValid()) {
                    callLoginApi(
                        fragment_login_etEmail.text.toString().trim(),
                        fragment_login_etPassword.text.toString().trim(),
                        "1564464646446"
                    )
                }

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
     * This method is to check if the input feild are valid or not
     */
    private fun isValid(): Boolean {
        val email = fragment_login_etEmail.text.toString().trim()
        val password = fragment_login_etPassword.text.toString().trim()

        if (!Utils.isEmailValid(email)) {
            Utils.showSnackBar(
                activity!!, fragment_login_btnLogin, true,
                "Please enter a valid email."
            )
            return false
        } else if (TextUtils.isEmpty(password)) {
            Utils.showSnackBar(
                activity!!, fragment_login_btnLogin, true,
                "Please enter a valid password."
            )
            return false
        } else {
            return true
        }
    }

    /**
     * This method is to check if the user name is exist of not
     */
    private fun callLoginApi(userEmail: String, password: String, deviceId: String) {
        val progressDialog = ProgressDialog(activity, R.style.ProgressDialog)

        if (!activity!!.isFinishing) {
            progressDialog.setCancelable(false)
            progressDialog.show()
            progressDialog.setContentView(R.layout.layout_progress_indicator)
        }

        WSUtils.getClient().logIn(userEmail, password, deviceId).enqueue(object :
            Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                progressDialog.dismiss()

                val jsonObject = WSUtils.getResponseJSON(response)

                if (jsonObject != null) {
                    val checkUserNameResponseModel = Gson().fromJson<DataModel.LoginResponseModel>(
                        jsonObject.toString(),
                        DataModel.LoginResponseModel::class.java!!
                    )
                    if (checkUserNameResponseModel.Success != null && checkUserNameResponseModel.Success == 1) {
                        Log.d("@@@", "Success")
                        Preference.instance.setData(
                            Preference.instance.PREF_USER_ID,
                            checkUserNameResponseModel.user_id!!
                        )

                        Preference.instance.setData(
                            Preference.instance.PREF_ACCOUNT_TYPE,
                            checkUserNameResponseModel.account_type!!
                        )

                        Preference.instance.setData(
                            Preference.instance.PREF_RENEW_DATE,
                            checkUserNameResponseModel.renew_date!!
                        )

                        Preference.instance.setIntData(
                            Preference.instance.PREF_SERVICE_ID,
                            checkUserNameResponseModel.service_id!!
                        )

                        Preference.instance.isLogin = true
                        goToHomeActivity()
                    } else {
                        Utils.showSnackBar(
                            activity!!, fragment_login_btnLogin, true,
                            checkUserNameResponseModel.message!!
                        )
                    }

                } else {
                    Utils.showSnackBar(
                        activity!!,
                        fragment_login_btnLogin,
                        true,
                        getString(R.string.alert_some_error)
                    )
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                progressDialog.dismiss()
                Utils.showSnackBar(
                    activity!!,
                    fragment_login_btnLogin,
                    true,
                    getString(R.string.alert_some_error)
                )
            }
        })
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