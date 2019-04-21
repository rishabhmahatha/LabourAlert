package com.labouralerts.ui.fragment

import android.app.ProgressDialog
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.google.gson.Gson
import com.labouralerts.R
import com.labouralerts.ui.adapter.NotificationAdapter
import com.labouralerts.ui.adapter.Top10ListAdapter
import com.labouralerts.ui.adapter.Top10StateListAdapter
import com.labouralerts.ui.model.DataModel
import com.labouralerts.utils.Preference
import com.labouralerts.utils.Utils
import com.labouralerts.webservice.WSUtils
import kotlinx.android.synthetic.main.fragment_notification.*
import kotlinx.android.synthetic.main.fragment_search_result.*
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_top_10.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Top10Fragment : BaseFragments(), Top10ListAdapter.OnItemClick, Top10StateListAdapter.OnItemClick {
    private var top10Result: DataModel.Top10Result? = null
    private var companyResult: DataModel.Company? = null
    private var stateResult: DataModel.State? = null
    private var arrCompanyList = ArrayList<DataModel._1>()
    private var arrStateList = ArrayList<DataModel._1_>()

    private var top10Adapter: Top10ListAdapter? = null
    private var top10StateAdapter: Top10StateListAdapter? = null


    override fun defineLayoutResource(): Int {
        return R.layout.fragment_top_10
    }

    override fun initializeComponent(view: View) {
        fragment_top_10_btnCompanies.setOnClickListener(this)
        fragment_top_10_btnStates.setOnClickListener(this)
        fragment_top_10_tv1M.setOnClickListener(this)
        fragment_top_10_tv1Y.setOnClickListener(this)
        fragment_top_10_tv2M.setOnClickListener(this)
        fragment_top_10_tv3M.setOnClickListener(this)

        fragment_top_10_btnCompanies.isSelected = true
        fragment_top_10_tv1M.isSelected = true

        fragment_top_10_tv1M.isSelected = true
        fragment_top_10_tv1Y.isSelected = false
        fragment_top_10_tv2M.isSelected = false
        fragment_top_10_tv3M.isSelected = false

        val viewManager = LinearLayoutManager(activity)

        top10Adapter = Top10ListAdapter(activity!!, this, arrCompanyList)
        fragment_top_10_rvTop10CompanyList.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = top10Adapter
        }


        val viewManagerState = LinearLayoutManager(activity)
        top10StateAdapter = Top10StateListAdapter(activity!!, this, arrStateList)
        fragment_top_10_rvTop10StateList.apply {
            setHasFixedSize(true)
            layoutManager = viewManagerState
            adapter = top10StateAdapter
        }

        if (Preference.instance.isLogin) {
            callTop10Result(Preference.instance.getStringData(Preference.instance.PREF_USER_ID, "1")!!)
        } else {
            callTop10Result("1")
        }
    }

    override fun onClick(view: View) {
        super.onClick(view)
        when (view.id) {
            R.id.fragment_top_10_btnCompanies -> {
                fragment_top_10_btnCompanies.isSelected = true
                fragment_top_10_btnStates.isSelected = false

                fragment_top_10_rvTop10StateList.visibility = View.INVISIBLE
                fragment_top_10_rvTop10CompanyList.visibility = View.VISIBLE


                if (fragment_top_10_tv1M.isSelected) {
                    arrCompanyList.clear()
                    arrCompanyList.addAll(companyResult!!._1!!)
                } else if (fragment_top_10_tv2M.isSelected) {
                    arrCompanyList.clear()
                    arrCompanyList.addAll(companyResult!!._2!!)
                } else if (fragment_top_10_tv3M.isSelected) {
                    arrCompanyList.clear()
                    arrCompanyList.addAll(companyResult!!._3!!)
                } else if (fragment_top_10_tv1Y.isSelected) {
                    arrCompanyList.clear()
                    arrCompanyList.addAll(companyResult!!._12!!)
                }

            }

            R.id.fragment_top_10_btnStates -> {
                fragment_top_10_btnCompanies.isSelected = false
                fragment_top_10_btnStates.isSelected = true

                fragment_top_10_rvTop10StateList.visibility = View.VISIBLE
                fragment_top_10_rvTop10CompanyList.visibility = View.INVISIBLE

                if (fragment_top_10_tv1M.isSelected) {
                    arrStateList.clear()
                    arrStateList.addAll(stateResult!!._1!!)
                } else if (fragment_top_10_tv2M.isSelected) {
                    arrStateList.clear()
                    arrStateList.addAll(stateResult!!._2!!)
                } else if (fragment_top_10_tv3M.isSelected) {
                    arrStateList.clear()
                    arrStateList.addAll(stateResult!!._3!!)
                } else if (fragment_top_10_tv1Y.isSelected) {
                    arrStateList.clear()
                    arrStateList.addAll(stateResult!!._12!!)
                }
            }

            R.id.fragment_top_10_tv1M -> {
                fragment_top_10_tv1M.isSelected = true
                fragment_top_10_tv1Y.isSelected = false
                fragment_top_10_tv2M.isSelected = false
                fragment_top_10_tv3M.isSelected = false

                if (fragment_top_10_btnCompanies.isSelected) {
                    arrCompanyList.clear()
                    arrCompanyList.addAll(companyResult!!._1!!)
                } else {
                    arrStateList.clear()
                    arrStateList.addAll(stateResult!!._1!!)
                }
            }

            R.id.fragment_top_10_tv1Y -> {
                fragment_top_10_tv1M.isSelected = false
                fragment_top_10_tv1Y.isSelected = true
                fragment_top_10_tv2M.isSelected = false
                fragment_top_10_tv3M.isSelected = false

                if (fragment_top_10_btnCompanies.isSelected) {
                    arrCompanyList.clear()
                    arrCompanyList.addAll(companyResult!!._12!!)
                } else {
                    arrStateList.clear()
                    arrStateList.addAll(stateResult!!._12!!)
                }
            }

            R.id.fragment_top_10_tv2M -> {
                fragment_top_10_tv1M.isSelected = false
                fragment_top_10_tv1Y.isSelected = false
                fragment_top_10_tv2M.isSelected = true
                fragment_top_10_tv3M.isSelected = false

                if (fragment_top_10_btnCompanies.isSelected) {
                    arrCompanyList.clear()
                    arrCompanyList.addAll(companyResult!!._2!!)
                } else {
                    arrStateList.clear()
                    arrStateList.addAll(stateResult!!._2!!)
                }
            }

            R.id.fragment_top_10_tv3M -> {
                fragment_top_10_tv1M.isSelected = false
                fragment_top_10_tv1Y.isSelected = false
                fragment_top_10_tv2M.isSelected = false
                fragment_top_10_tv3M.isSelected = true

                if (fragment_top_10_btnCompanies.isSelected) {
                    arrCompanyList.clear()
                    arrCompanyList.addAll(companyResult!!._3!!)
                } else {
                    arrStateList.clear()
                    arrStateList.addAll(stateResult!!._3!!)
                }
            }


        }
        top10StateAdapter!!.notifyDataSetChanged()
        top10Adapter!!.notifyDataSetChanged()
    }


    //Top 10 company list item click
    override fun onSaveAlertClick(position: Int, view: View) {
        if (arrCompanyList[position].alertStatus.equals("Active")) {
            arrCompanyList[position].alertStatus = ""
            top10Adapter!!.notifyDataSetChanged()
            callDeleteAlertApi(
                arrCompanyList[position].businessId!!,
                "Company",
                Preference.instance.getStringData(Preference.instance.PREF_USER_ID, "1")!!
            )
        } else {
            arrCompanyList[position].alertStatus = "Active"
            top10Adapter!!.notifyDataSetChanged()
            callSaveAlertApi(
                arrCompanyList[position].businessId!!,
                "Active",
                "Company",
                Preference.instance.getStringData(Preference.instance.PREF_USER_ID, "1")!!
            )
        }

    }

    override fun onSaveStateAlertClick(position: Int, view: View) {
        if (arrStateList[position].alertStatus.equals("Active")) {
            arrStateList[position].alertStatus = ""
            top10StateAdapter!!.notifyDataSetChanged()
            callDeleteAlertApi(
                arrStateList[position].businessId!!,
                "State",
                Preference.instance.getStringData(Preference.instance.PREF_USER_ID, "1")!!
            )
        } else {
            arrStateList[position].alertStatus = "Active"
            top10StateAdapter!!.notifyDataSetChanged()
            callSaveAlertApi(
                arrStateList[position].businessId!!,
                "Active",
                "State",
                Preference.instance.getStringData(Preference.instance.PREF_USER_ID, "1")!!
            )
        }


    }

    /**
     * This method is to call the top10 list
     */
    private fun callTop10Result(userId: String) {
        val progressDialog = ProgressDialog(activity, R.style.ProgressDialog)

        if (!activity!!.isFinishing) {
            progressDialog.setCancelable(false)
            progressDialog.show()
            progressDialog.setContentView(R.layout.layout_progress_indicator)
        }

        WSUtils.getClient().top10Result(userId).enqueue(object :
            Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                progressDialog.dismiss()

                val jsonObject = WSUtils.getResponseJSON(response)

                if (jsonObject != null) {
                    val checkUserNameResponseModel = Gson().fromJson<DataModel.Top10Result>(
                        jsonObject.toString(),
                        DataModel.Top10Result::class.java!!
                    )
                    if (checkUserNameResponseModel.success != null && checkUserNameResponseModel.success == 1) {
                        Log.d("@@@", "Success")
                        top10Result = checkUserNameResponseModel
                        companyResult = top10Result!!.company
                        stateResult = top10Result!!.state
                        arrCompanyList.addAll(companyResult!!._1!!)
                        arrStateList.addAll(stateResult!!._1!!)
                        top10StateAdapter!!.notifyDataSetChanged()
                        top10Adapter!!.notifyDataSetChanged()

                    } else {
                        Utils.showSnackBar(
                            activity!!, fragment_signup_tvTermsConditions, true,
                            checkUserNameResponseModel.message!!
                        )
                    }

                } else {
                    Utils.showSnackBar(
                        activity!!,
                        fragment_signup_tvTermsConditions,
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

    private fun callSaveAlertApi(businessId: String, status: String, service: String, userId: String) {
        val progressDialog = ProgressDialog(activity, R.style.ProgressDialog)

        if (!activity!!.isFinishing) {
            progressDialog.setCancelable(false)
            progressDialog.show()
            progressDialog.setContentView(R.layout.layout_progress_indicator)
        }

        WSUtils.getClient().saveAlert(businessId, status, service, userId).enqueue(object :
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
                        Utils.showSnackBar(
                            activity!!, fragment_top_10_tv1M, false,
                            checkUserNameResponseModel.message!!
                        )

                    } else {
                        Utils.showSnackBar(
                            activity!!, fragment_top_10_tv1M, true,
                            checkUserNameResponseModel.message!!
                        )
                    }

                } else {
                    Utils.showSnackBar(
                        activity!!,
                        fragment_top_10_tv1M,
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

    private fun callDeleteAlertApi(businessId: String, service: String, userId: String) {
        val progressDialog = ProgressDialog(activity, R.style.ProgressDialog)

        if (!activity!!.isFinishing) {
            progressDialog.setCancelable(false)
            progressDialog.show()
            progressDialog.setContentView(R.layout.layout_progress_indicator)
        }

        WSUtils.getClient().deleteAlert(businessId, service, userId).enqueue(object :
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
                        Utils.showSnackBar(
                            activity!!, fragment_signup_tvTermsConditions, false,
                            checkUserNameResponseModel.message!!
                        )

                    } else {
                        Utils.showSnackBar(
                            activity!!, fragment_signup_tvTermsConditions, true,
                            checkUserNameResponseModel.message!!
                        )
                    }

                } else {
                    Utils.showSnackBar(
                        activity!!,
                        fragment_signup_tvTermsConditions,
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