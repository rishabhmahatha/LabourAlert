package com.labouralerts.ui.fragment

import android.app.ProgressDialog
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.google.gson.Gson
import com.labouralerts.R
import com.labouralerts.ui.adapter.NotificationAdapter
import com.labouralerts.ui.model.DataModel
import com.labouralerts.utils.Preference
import com.labouralerts.utils.Utils
import com.labouralerts.webservice.WSUtils
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_notification.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationFragment : BaseFragments() {
    val arrNotificationList = ArrayList<DataModel.AlertNotificationModel>()
    lateinit var notificationListAdapter: NotificationAdapter

    override fun defineLayoutResource(): Int {
        return R.layout.fragment_notification
    }

    override fun initializeComponent(view: View) {
        val viewManager = LinearLayoutManager(activity)
        notificationListAdapter = NotificationAdapter(activity!!, arrNotificationList)
        fragment_notification_rvNotificationList.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = notificationListAdapter
        }

        callNotificationApi()
    }


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

        WSUtils.getClient().alertNotification(userId).enqueue(object :
            Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                progressDialog.dismiss()

                val jsonObject = WSUtils.getResponseJSON(response)

                if (jsonObject != null) {
                    val alertNotification = Gson().fromJson<DataModel.AlertNotification>(
                        jsonObject.toString(),
                        DataModel.AlertNotification::class.java!!
                    )

                    if (alertNotification != null)
                        arrNotificationList.addAll(alertNotification.alertNotification!!)
                        notificationListAdapter.notifyDataSetChanged()

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


}