package com.labouralerts.ui.fragment

import android.app.ProgressDialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import com.google.gson.Gson
import com.labouralerts.R
import com.labouralerts.ui.model.DataModel
import com.labouralerts.utils.Constants
import com.labouralerts.utils.Preference
import com.labouralerts.utils.Utils
import com.labouralerts.webservice.WSUtils
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_sign_up.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : BaseFragments() {

    override fun defineLayoutResource(): Int {
        return R.layout.fragment_search
    }

    override fun initializeComponent(view: View) {
        fragment_search_btnSearch.setOnClickListener(this)
    }


    override fun onClick(view: View) {
        super.onClick(view)
        when (view.id) {

            R.id.fragment_search_btnSearch -> {
                val search = fragment_search_etSearch.text.toString().trim()
                if (isValid()) {
                    if (Preference.instance.isLogin) {
                        callBasicSearchApi(
                            search,
                            Preference.instance.getStringData(Preference.instance.PREF_USER_ID, "1")!!
                        )
                    } else {
                        callBasicSearchApi(search, "1")
                    }

                }

            }
        }
    }

    /**
     * This method is to redirect user to search result screen
     */
    private fun goToSearchResultScreen(basicSearchMainModel: DataModel.BasicSearchMainModel) {
        val fragment = SearchResultFragment()
        val bundle = Bundle()
        bundle.putSerializable(Constants.BASIC_SEARCH_RESULT_MODEL, basicSearchMainModel)
        fragment.arguments = bundle
        addFragment(R.id.flContainer, this, fragment, false)
    }

    /**
     * This method is to check if the inout feild are valid or not
     */
    private fun isValid(): Boolean {
        val search = fragment_search_etSearch.text.toString().trim()

        if (TextUtils.isEmpty(search)) {
            Utils.showSnackBar(
                activity!!,
                fragment_search_btnSearch,
                true,
                "Please enter something to search."
            )
            return false
        } else {
            return true
        }
    }

    /*
    This method is to call the basic search api
     */
    private fun callBasicSearchApi(searchField: String, userId: String) {
        val progressDialog = ProgressDialog(activity, R.style.ProgressDialog)

        if (!activity!!.isFinishing) {
            progressDialog.setCancelable(false)
            progressDialog.show()
            progressDialog.setContentView(R.layout.layout_progress_indicator)
        }

        WSUtils.getClient().basicSearch(searchField, userId).enqueue(object :
            Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                progressDialog.dismiss()

                val jsonObject = WSUtils.getResponseJSON(response)

                if (jsonObject != null) {
                    val checkUserNameResponseModel = Gson().fromJson<DataModel.BasicSearchMainModel>(
                        jsonObject.toString(),
                        DataModel.BasicSearchMainModel::class.java!!
                    )
                    if (checkUserNameResponseModel.success != null && checkUserNameResponseModel.success == 1) {
                        Log.d("@@@", "Success")
                        goToSearchResultScreen(checkUserNameResponseModel)
                    } else {
                        Utils.showSnackBar(
                            activity!!, fragment_search_btnSearch, true,
                            checkUserNameResponseModel.message!!
                        )
                    }

                } else {
                    Utils.showSnackBar(
                        activity!!,
                        fragment_search_btnSearch,
                        true,
                        getString(R.string.alert_some_error)
                    )
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                progressDialog.dismiss()
                Utils.showSnackBar(
                    activity!!,
                    fragment_signup_tvTermsConditions,
                    true,
                    getString(R.string.alert_some_error)
                )
            }
        })
    }

}