package com.labouralerts.ui.fragment

import android.app.ProgressDialog
import android.content.Intent
import android.text.Html
import android.util.Log
import android.view.View
import com.google.firebase.iid.FirebaseInstanceId
import com.google.gson.Gson
import com.labouralerts.R
import com.labouralerts.ui.activity.HomeActivity
import com.labouralerts.ui.activity.TcPrivacyAboutUsActivity
import com.labouralerts.ui.model.DataModel
import com.labouralerts.utils.Constants
import com.labouralerts.utils.Preference
import com.labouralerts.utils.Utils
import com.labouralerts.webservice.WSConstants
import com.labouralerts.webservice.WSUtils
import kotlinx.android.synthetic.main.fragment_sign_up.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpFragment : BaseFragments() {
    override fun defineLayoutResource(): Int {
        return R.layout.fragment_sign_up
    }

    override fun initializeComponent(view: View) {
        fragment_signup_btnSignUp.setOnClickListener(this)
        fragment_signup_tvGoToSignIn.setOnClickListener(this)
        fragment_signup_tvPP.setOnClickListener(this)
        fragment_signup_tvTC.setOnClickListener(this)

    }

    /**
     * This method is to check if the input feild are valid or not
     */
    private fun isValid(): Boolean {
        val email = fragment_signup_etEmail.text.toString().trim()
        val confirmEmail = fragment_signup_etConfirmEmail.text.toString().trim()

        if (!Utils.isEmailValid(email)) {
            Utils.showSnackBar(
                activity!!, fragment_signup_tvGoToSignIn, true,
                "Please enter a valid email."
            )
            return false
        } else if (!confirmEmail.equals(email)) {
            Utils.showSnackBar(
                activity!!, fragment_signup_tvGoToSignIn, true,
                "Email and confirm email must be same."
            )
            return false
        } else {
            return true
        }
    }

    override fun onClick(view: View) {
        super.onClick(view)
        val id = view.id

        when (id) {

            R.id.fragment_signup_btnSignUp -> {
                if (isValid()) {
                    checkUserNameExist(fragment_signup_etEmail.text.toString().trim())
                }
            }

            R.id.fragment_signup_tvGoToSignIn -> {
                activity!!.onBackPressed()
            }

            R.id.fragment_signup_tvPP -> {
                val intent = Intent(activity, TcPrivacyAboutUsActivity::class.java);
                intent.putExtra(Constants.TITLE,"Privacy Policy")
                startActivity(intent)
            }

            R.id.fragment_signup_tvTC -> {
                val intent = Intent(activity, TcPrivacyAboutUsActivity::class.java);
                intent.putExtra(Constants.TITLE,"Terms and Conditions")
                startActivity(intent)
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

    /**
     * This method is to check if the user name is exist of not
     */
    private fun checkUserNameExist(userName: String) {
        val progressDialog = ProgressDialog(activity, R.style.ProgressDialog)

        if (!activity!!.isFinishing) {
            progressDialog.setCancelable(false)
            progressDialog.show()
            progressDialog.setContentView(R.layout.layout_progress_indicator)
        }

        WSUtils.getClient().checkUserName(userName).enqueue(object :
            Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                progressDialog.dismiss()

                val jsonObject = WSUtils.getResponseJSON(response)

                if (jsonObject != null) {
                    val checkUserNameResponseModel = Gson().fromJson<DataModel.CheckUserNameResponseModel>(
                        jsonObject.toString(),
                        DataModel.CheckUserNameResponseModel::class.java!!
                    )
                    if (checkUserNameResponseModel.Success != null && checkUserNameResponseModel.Success == 1) {
                        Log.d("@@@", "Success")
                        callSignUpApi(
                            fragment_signup_etEmail.text.toString().trim(),
                            "WY",
                            fragment_signup_etEmail.text.toString().trim(),
                            "1",
                            "2968 Pheasant Dr",
                            "Casper",
                            ((System.currentTimeMillis() / 1000) + 604800000L).toString(),
                            "82604", "USA", "Approved", FirebaseInstanceId.getInstance().token!!, "132155464",
                            (((System.currentTimeMillis() / 1000) + 604800000L) * 30).toString(), "123456"
                        )
                    } else {
                        Utils.showSnackBar(
                            activity!!, fragment_signup_tvGoToSignIn, true,
                            checkUserNameResponseModel.message!!
                        )
                    }

                } else {
                    Utils.showSnackBar(
                        activity!!,
                        fragment_signup_tvGoToSignIn,
                        true,
                        getString(R.string.alert_some_error)
                    )
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                progressDialog.dismiss()
                Utils.showSnackBar(
                    activity!!,
                    fragment_signup_tvGoToSignIn,
                    true,
                    getString(R.string.alert_some_error)
                )
            }
        })
    }


    /**
     * This method is to check if the user name is exist of not
     */
    private fun callSignUpApi(
        email: String,
        state: String,
        user_name: String,
        service_id: String,
        address1: String,
        city: String,
        renew_date: String,
        zip_code: String,
        country: String,
        profile_status: String,
        device_id: String,
        transaction_id: String,
        transaction_date: String,
        password: String
    ) {
        val progressDialog = ProgressDialog(activity, R.style.ProgressDialog)

        if (!activity!!.isFinishing) {
            progressDialog.setCancelable(false)
            progressDialog.show()
            progressDialog.setContentView(R.layout.layout_progress_indicator)
        }

        WSUtils.getClient().signUp(
            email,
            state,
            user_name,
            service_id,
            address1,
            city,
            renew_date,
            zip_code,
            country,
            profile_status,
            device_id,
            transaction_id,
            transaction_date,
            password
        ).enqueue(object :
            Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                progressDialog.dismiss()

                val jsonObject = WSUtils.getResponseJSON(response)

                if (jsonObject != null) {
                    val checkUserNameResponseModel = Gson().fromJson<DataModel.SignUpResponseModel>(
                        jsonObject.toString(),
                        DataModel.SignUpResponseModel::class.java!!
                    )
                    if (checkUserNameResponseModel.Success != null && checkUserNameResponseModel.Success == 1) {
                        Preference.instance.setData(
                            Preference.instance.PREF_USER_ID,
                            checkUserNameResponseModel.userid!!
                        )

                        Preference.instance.setData(
                            Preference.instance.PREF_ACCOUNT_TYPE,
                            checkUserNameResponseModel.account_type!!
                        )

                        Preference.instance.setData(
                            Preference.instance.PREF_RENEW_DATE,
                            checkUserNameResponseModel.renew_date!!
                        )

                        Preference.instance.isLogin = true

                        goToHomeActivity()
                        Log.d("@@@", "Success")
                    } else {
                        Utils.showSnackBar(
                            activity!!, fragment_signup_tvGoToSignIn, true,
                            checkUserNameResponseModel.message!!
                        )
                    }

                } else {
                    Utils.showSnackBar(
                        activity!!,
                        fragment_signup_tvGoToSignIn,
                        true,
                        getString(R.string.alert_some_error)
                    )
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                progressDialog.dismiss()
                Utils.showSnackBar(
                    activity!!,
                    fragment_signup_tvGoToSignIn,
                    true,
                    getString(R.string.alert_some_error)
                )
            }
        })
    }

}