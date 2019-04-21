package com.labouralerts.ui.fragment

import android.app.Notification
import android.app.ProgressDialog
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.google.gson.Gson
import com.labouralerts.R
import com.labouralerts.ui.adapter.NotificationCompanyAdapter
import com.labouralerts.ui.adapter.NotificationStateAdapter
import com.labouralerts.ui.adapter.ResultsAdapter
import com.labouralerts.ui.model.DataModel
import com.labouralerts.utils.Preference
import com.labouralerts.utils.Utils
import com.labouralerts.webservice.WSUtils
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.android.synthetic.main.fragment_alerts_manager.*
import kotlinx.android.synthetic.main.fragment_search_result.*
import kotlinx.android.synthetic.main.fragment_sign_up.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlertManagerFragment : BaseFragments() {
    private val arrListCompany: ArrayList<DataModel.NotificationCompany> = ArrayList()
    private val arrListState: ArrayList<DataModel.NotificationState> = ArrayList()
    private lateinit var notificationModel: DataModel.Notification

    override fun defineLayoutResource(): Int {
        return R.layout.fragment_alerts_manager
    }

    override fun initializeComponent(view: View) {
        fragment_alerts_manager_llCompany.setOnClickListener(this)
        fragment_alerts_manager_llStates.setOnClickListener(this)

        callNotificationApi()
    }

    override fun onClick(view: View) {
        super.onClick(view)

        when (view.id) {
            R.id.fragment_alerts_manager_llCompany -> {
                if (fragment_alerts_manager_llCompanyChild.visibility == View.VISIBLE) {
                    fragment_alerts_manager_llCompanyChild.visibility = View.GONE
                    fragment_alerts_manager_ivArrowCompany.setBackgroundResource(R.drawable.ic_right_arrow)
                } else {
                    fragment_alerts_manager_llCompanyChild.visibility = View.VISIBLE
                    setCompanyAdapter()
                    fragment_alerts_manager_ivArrowCompany.setBackgroundResource(R.drawable.ic_down_arrow)
                }
            }

            R.id.fragment_alerts_manager_llStates -> {
                if (fragment_alerts_manager_llStatesChild.visibility == View.VISIBLE) {
                    fragment_alerts_manager_llStatesChild.visibility = View.GONE
                    fragment_alerts_manager_ivArrowStates.setBackgroundResource(R.drawable.ic_right_arrow)
                } else {
                    fragment_alerts_manager_llStatesChild.visibility = View.VISIBLE
                    setStateAdapter()
                    fragment_alerts_manager_ivArrowStates.setBackgroundResource(R.drawable.ic_down_arrow)
                }

            }
        }
    }

    /**
     * This method is to call the notification api
     */
    private fun callNotificationApi() {
        var userId = "1"
        if (Preference.instance.isLogin) {
            userId = Preference.instance.getStringData(Preference.instance.PREF_USER_ID, "1")!!
        }
        val progressDialog = ProgressDialog(activity, R.style.ProgressDialog)

        if (!activity!!.isFinishing) {
            progressDialog.setCancelable(false)
            progressDialog.show()
            progressDialog.setContentView(R.layout.layout_progress_indicator)
        }

        WSUtils.getClient().notification(userId).enqueue(object :
            Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                progressDialog.dismiss()

                val jsonObject = WSUtils.getResponseJSON(response)

                if (jsonObject != null) {
                    val checkUserNameResponseModel = Gson().fromJson<DataModel.Notification>(
                        jsonObject.toString(),
                        DataModel.Notification::class.java!!
                    )
                    if (checkUserNameResponseModel.success != null && checkUserNameResponseModel.success == 1) {
                        Log.d("@@@", "Success")
                        notificationModel = checkUserNameResponseModel
                        arrListCompany.addAll(notificationModel.company!!)
                        arrListState.addAll(notificationModel.state!!)

                    } else {
                        Utils.showSnackBar(
                            activity!!, fragment_alerts_manager_llStates, true,
                            checkUserNameResponseModel.message!!
                        )
                    }

                } else {
                    Utils.showSnackBar(
                        activity!!,
                        fragment_alerts_manager_llStates,
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

    /**
     * This method is to set the state adapter
     */
    private fun setStateAdapter() {
        val viewManager = LinearLayoutManager(activity)
        val resultsAdapter = NotificationStateAdapter(activity!!, arrListState)
        fragment_alert_manager_rvNotificationState.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = resultsAdapter
        }

    }

    /**
     * This method is to set the state adapter
     */
    private fun setCompanyAdapter() {
        val viewManager = LinearLayoutManager(activity)
        val resultsAdapter = NotificationCompanyAdapter(activity!!, arrListCompany)
        fragment_alert_manager_rvNotificationCompany.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = resultsAdapter
        }
    }

}