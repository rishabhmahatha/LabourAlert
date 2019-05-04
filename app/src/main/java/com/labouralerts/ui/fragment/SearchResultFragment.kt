package com.labouralerts.ui.fragment

import android.app.ProgressDialog
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.google.gson.Gson
import com.labouralerts.R
import com.labouralerts.ui.activity.HomeActivity
import com.labouralerts.ui.adapter.GraphResultAdapter
import com.labouralerts.ui.adapter.NotificationAdapter
import com.labouralerts.ui.adapter.ResultsAdapter
import com.labouralerts.ui.model.DataModel
import com.labouralerts.utils.Constants
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

class SearchResultFragment : BaseFragments(), ResultsAdapter.OnItemClick {
    var viewManager: LinearLayoutManager? = null
    var viewManagerGraph: LinearLayoutManager? = null
    var basicSearchMainModel: DataModel.BasicSearchMainModel? = null
    var advanceSearchMainModel: DataModel.AdvanceSearchMainModel? = null
    var arrListBasicSearchMainModel: ArrayList<DataModel.SearchCompanyCityState> = ArrayList()
    var resultsAdapter: ResultsAdapter? = null

    override fun defineLayoutResource(): Int {
        return R.layout.fragment_search_result
    }

    override fun initializeComponent(view: View) {
        if (activity is HomeActivity) {
            (activity as HomeActivity).manageToolbarVisibility(true)
        }

        if (arguments != null && arguments!!.containsKey(Constants.BASIC_SEARCH_RESULT_MODEL)
            && arguments!!.containsKey(Constants.IS_BASIC_SEARCH_RESULT_MODEL)
        ) {
            if(arguments!!.getBoolean(Constants.IS_BASIC_SEARCH_RESULT_MODEL)){
                basicSearchMainModel =
                    arguments!!.getSerializable(Constants.BASIC_SEARCH_RESULT_MODEL) as DataModel.BasicSearchMainModel?
                arrListBasicSearchMainModel.addAll(basicSearchMainModel!!.data!!.company!!)
                arrListBasicSearchMainModel.addAll(basicSearchMainModel!!.data!!.city!!)
                arrListBasicSearchMainModel.addAll(basicSearchMainModel!!.data!!.state!!)
            }else{
                advanceSearchMainModel =
                    arguments!!.getSerializable(Constants.BASIC_SEARCH_RESULT_MODEL) as DataModel.AdvanceSearchMainModel?
                arrListBasicSearchMainModel.addAll(advanceSearchMainModel!!.data!!)
            }

        }

        viewManager = LinearLayoutManager(activity)
        resultsAdapter = ResultsAdapter(activity!!, this, arrListBasicSearchMainModel)
        fragment_search_result_rvSearchResult.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = resultsAdapter
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        if (activity is HomeActivity) {
            (activity as HomeActivity).manageToolbarVisibility(false)
        }
    }


    /**
     * This methos is to show hide the graph and result recycler view
     */
    fun manageGraphResultListView(isGraphResult: Boolean) {
        if (isGraphResult) {
            fragment_search_result_rvGraphResult.visibility = View.VISIBLE
            fragment_search_result_rvSearchResult.visibility = View.GONE

            viewManagerGraph = LinearLayoutManager(activity)
            val graphAdapter = GraphResultAdapter(activity!!, arrListBasicSearchMainModel)
            fragment_search_result_rvGraphResult.apply {
                setHasFixedSize(true)
                layoutManager = viewManagerGraph
                adapter = graphAdapter
            }

        } else {
            fragment_search_result_rvGraphResult.visibility = View.GONE
            fragment_search_result_rvSearchResult.visibility = View.VISIBLE

            viewManager = LinearLayoutManager(activity)
            val resultsAdapter = ResultsAdapter(activity!!, this, arrListBasicSearchMainModel)
            fragment_search_result_rvSearchResult.apply {
                setHasFixedSize(true)
                layoutManager = viewManager
                adapter = resultsAdapter
            }
        }
    }

    override fun onSaveAlertClick(position: Int, view: View) {
        if (arrListBasicSearchMainModel[position].alertStatus.equals("Active")) {
            arrListBasicSearchMainModel[position].alertStatus = ""
            resultsAdapter!!.notifyDataSetChanged()
            callDeleteAlertApi(
                arrListBasicSearchMainModel[position].businessId.toString(),
                "Active",
                Preference.instance.getStringData(Preference.instance.PREF_USER_ID, "1")!!
            )
        } else {
            arrListBasicSearchMainModel[position].alertStatus = "Active"
            resultsAdapter!!.notifyDataSetChanged()
            callSaveAlertApi(
                arrListBasicSearchMainModel[position].businessId.toString(),
                "Active",
                "Company",
                Preference.instance.getStringData(Preference.instance.PREF_USER_ID, "1")!!
            )
        }

        Log.d(
            "AAAAAA",
            arrListBasicSearchMainModel[position].businessId.toString() + "," + Preference.instance.getStringData(
                Preference.instance.PREF_USER_ID,
                "1"
            )!!
        )
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
                            activity!!, fragment_search_result_rvSearchResult, false,
                            checkUserNameResponseModel.message!!
                        )

                    } else {
                        Utils.showSnackBar(
                            activity!!, fragment_search_result_rvSearchResult, true,
                            checkUserNameResponseModel.message!!
                        )
                    }

                } else {
                    Utils.showSnackBar(
                        activity!!,
                        fragment_search_result_rvSearchResult,
                        true,
                        getString(R.string.alert_some_error)
                    )
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                progressDialog.dismiss()
                Utils.showSnackBar(
                    activity!!,
                    fragment_search_result_rvSearchResult,
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
                            activity!!, fragment_search_result_rvSearchResult, false,
                            checkUserNameResponseModel.message!!
                        )

                    } else {
                        Utils.showSnackBar(
                            activity!!, fragment_search_result_rvSearchResult, true,
                            checkUserNameResponseModel.message!!
                        )
                    }

                } else {
                    Utils.showSnackBar(
                        activity!!,
                        fragment_search_result_rvSearchResult,
                        true,
                        getString(R.string.alert_some_error)
                    )
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                progressDialog.dismiss()
                Utils.showSnackBar(
                    activity!!,
                    fragment_search_result_rvSearchResult,
                    true,
                    getString(R.string.alert_some_error)
                )
            }
        })
    }

}