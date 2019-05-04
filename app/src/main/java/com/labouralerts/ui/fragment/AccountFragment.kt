package com.labouralerts.ui.fragment

import android.app.ProgressDialog
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.google.gson.Gson
import com.labouralerts.R
import com.labouralerts.ui.activity.OptionActivity
import com.labouralerts.ui.activity.TcPrivacyAboutUsActivity
import com.labouralerts.ui.model.DataModel
import com.labouralerts.utils.Constants
import com.labouralerts.utils.Preference
import com.labouralerts.utils.Utils
import com.labouralerts.webservice.WSUtils
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.android.synthetic.main.fragment_sign_up.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountFragment : BaseFragments() {
    override fun defineLayoutResource(): Int {
        return R.layout.fragment_account
    }

    override fun initializeComponent(view: View) {
        fragment_account_btnChangePassword.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        super.onClick(view)
        val id = view.id

        when (id) {
            R.id.fragment_account_btnChangePassword -> {
                val currentPass = fragment_account_etCurrentPassword.text.toString().trim()
                val newPass = fragment_account_etNewPassword.text.toString().trim()
                val confirmPass = fragment_account_etConfirmPassword.text.toString().trim()
                if (isValid()) {
                    if (Preference.instance.isLogin) {
                        callChangePassword(
                            currentPass, newPass,
                            Preference.instance.getStringData(Preference.instance.PREF_USER_ID, "1")!!
                        )
                    } else {
                        callChangePassword(currentPass, newPass, "1")
                    }

                }
            }

            R.id.fragment_account_tvAboutUs->{
                val intent = Intent(activity, TcPrivacyAboutUsActivity::class.java);
                intent.putExtra(Constants.TITLE,"About Us")
                startActivity(intent)
            }
        }
    }

    /**
     * This method is to check if the input feild are valid or not
     */
    private fun isValid(): Boolean {
        val currentPass = fragment_account_etCurrentPassword.text.toString().trim()
        val newPass = fragment_account_etNewPassword.text.toString().trim()
        val confirmPass = fragment_account_etConfirmPassword.text.toString().trim()

        if (TextUtils.isEmpty(currentPass)) {
            Utils.showSnackBar(
                activity!!, fragment_account_btnChangePassword, true,
                "Please enter current password."
            )
            return false
        } else if (TextUtils.isEmpty(newPass)) {
            Utils.showSnackBar(
                activity!!, fragment_account_btnChangePassword, true,
                "Please enter new password."
            )
            return false
        } else if (!confirmPass.equals(newPass)) {
            Utils.showSnackBar(
                activity!!, fragment_account_btnChangePassword, true,
                "New password and validate password must be same."
            )
            return false
        } else {
            return true
        }
    }

    /**
     * This method is to call the change password api
     */
    private fun callChangePassword(oldPass: String, newPass: String, useId: String) {
        val progressDialog = ProgressDialog(activity, R.style.ProgressDialog)

        if (!activity!!.isFinishing) {
            progressDialog.setCancelable(false)
            progressDialog.show()
            progressDialog.setContentView(R.layout.layout_progress_indicator)
        }

        WSUtils.getClient().changePassword(oldPass, newPass, useId).enqueue(object :
            Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                progressDialog.dismiss()

                val jsonObject = WSUtils.getResponseJSON(response)

                if (jsonObject != null) {
                    val checkUserNameResponseModel = Gson().fromJson<DataModel.ChangePasswordResponseModel>(
                        jsonObject.toString(),
                        DataModel.ChangePasswordResponseModel::class.java!!
                    )
                    if (checkUserNameResponseModel.Success != null && checkUserNameResponseModel.Success == 1) {
                        Log.d("@@@", "Success")
                        fragment_account_etCurrentPassword.setText("")
                        fragment_account_etNewPassword.setText("")
                        fragment_account_etConfirmPassword.setText("")

                        Utils.showSnackBar(
                            activity!!, fragment_account_btnChangePassword, true,
                            checkUserNameResponseModel.message!!
                        )
                    } else {
                        Utils.showSnackBar(
                            activity!!, fragment_account_btnChangePassword, true,
                            checkUserNameResponseModel.message!!
                        )
                    }

                } else {
                    Utils.showSnackBar(
                        activity!!,
                        fragment_account_btnChangePassword,
                        true,
                        getString(R.string.alert_some_error)
                    )
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                progressDialog.dismiss()
                Utils.showSnackBar(
                    activity!!,
                    fragment_account_btnChangePassword,
                    true,
                    getString(R.string.alert_some_error)
                )
            }
        })
    }
}