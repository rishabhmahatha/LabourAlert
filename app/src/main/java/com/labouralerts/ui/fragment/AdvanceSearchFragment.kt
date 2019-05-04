package com.labouralerts.ui.fragment

import android.app.ProgressDialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.gson.Gson
import com.labouralerts.R
import com.labouralerts.ui.model.DataModel
import com.labouralerts.utils.Constants
import com.labouralerts.utils.Preference
import com.labouralerts.utils.Utils
import com.labouralerts.webservice.WSUtils
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.android.synthetic.main.fragment_advance_search.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_sign_up.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Timestamp
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*



class AdvanceSearchFragment : BaseFragments() {
    var selectedStatePosition = 0
    var selectedStartYearPosition = 0
    var selectedEndYearPosition = 0
    var selectedStartMonthPosition = 0
    var selectedEndMonthPosition = 0

    var city: Array<String>? = null
    var year: Array<String>? = null
    var month: Array<String>? = null

    override fun defineLayoutResource(): Int {
        return R.layout.fragment_advance_search
    }

    override fun initializeComponent(view: View) {
        fragment_advance_search_btnSearch.setOnClickListener(this)

        city = arrayOf(
            "State",
            "AL",
            "AK",
            "AZ",
            "AR",
            "CA",
            "CO",
            "CT",
            "D.C.",
            "DE",
            "FL",
            "GA",
            "HI",
            "ID",
            "IL",
            "IN",
            "IA",
            "KS",
            "KY",
            "LA",
            "ME",
            "MD",
            "MA",
            "MI",
            "MN",
            "MS",
            "MO",
            "MT",
            "NE",
            "NV",
            "NH",
            "NJ",
            "NM",
            "NY",
            "NC",
            "ND",
            "OH",
            "OK",
            "OR",
            "PA",
            "RI",
            "SC",
            "SD",
            "TN",
            "TX",
            "UT",
            "VT",
            "VA",
            "WA",
            "WV",
            "WI",
            "WY",
            "AS",
            "GU",
            "MP",
            "PR",
            "VI"
        )
        year = arrayOf(
            "Year",
            "1990",
            "1991",
            "1992",
            "1993",
            "1994",
            "1995",
            "1996",
            "1997",
            "1998",
            "1999",
            "2000",
            "2001",
            "2002",
            "2003",
            "2004",
            "2005",
            "2006",
            "2007",
            "2008",
            "2009",
            "2010",
            "2011",
            "2012",
            "2013",
            "2014",
            "2015",
            "2016",
            "2017",
            "2018",
            "2019",
            "2020",
            "2021",
            "2022",
            "2023",
            "2024",
            "2025",
            "2026",
            "2027",
            "2028",
            "2029",
            "2030"


        )
        month = arrayOf(
            "Month",
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
        )

        if (fragment_advance_search_spCity != null) {
            val arrayAdapter = ArrayAdapter(activity, R.layout.spinner_row, city)
            fragment_advance_search_spCity.adapter = arrayAdapter

            fragment_advance_search_spCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    selectedStatePosition = position
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Code to perform some action when nothing is selected
                }
            }
        }

        if (fragment_advance_search_spYearStart != null) {
            val arrayAdapter = ArrayAdapter(activity, R.layout.spinner_row, year)
            fragment_advance_search_spYearStart.adapter = arrayAdapter

            fragment_advance_search_spYearStart.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    selectedStartYearPosition = position
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Code to perform some action when nothing is selected
                }
            }
        }
        if (fragment_advance_search_spYearEnd != null) {
            val arrayAdapter = ArrayAdapter(activity, R.layout.spinner_row, year)
            fragment_advance_search_spYearEnd.adapter = arrayAdapter

            fragment_advance_search_spYearEnd.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                    selectedEndYearPosition = position

                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Code to perform some action when nothing is selected
                }
            }
        }

        if (fragment_advance_search_spMonthStart != null) {
            val arrayAdapter = ArrayAdapter(activity, R.layout.spinner_row, month)
            fragment_advance_search_spMonthStart.adapter = arrayAdapter

            fragment_advance_search_spMonthStart.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    selectedStartMonthPosition = position
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Code to perform some action when nothing is selected
                }
            }
        }
        if (fragment_advance_search_spMonthEnd != null) {
            val arrayAdapter = ArrayAdapter(activity, R.layout.spinner_row, month)
            fragment_advance_search_spMonthEnd.adapter = arrayAdapter

            fragment_advance_search_spMonthEnd.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                    selectedEndMonthPosition = position
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Code to perform some action when nothing is selected
                }
            }
        }
    }


    override fun onClick(view: View) {
        super.onClick(view)
        val id = view.id

        when (id) {

            fragment_advance_search_btnSearch.id -> {
                var userId = "1"
                if (Preference.instance.isLogin) {
                    userId = Preference.instance.getStringData(Preference.instance.PREF_USER_ID, "1")!!
                }

                Log.d("@@@@","1-" +
                        selectedStartMonthPosition
                        + "-" +
                        year!![selectedStartYearPosition])
                getTimeStampNew("1-" +
                        selectedStartMonthPosition
                        + "-" +
                        year!![selectedStartYearPosition])
                if (isValid()) {
                    callAdvanceSearchApi(
                        fragment_advance_search_etCompany.text!!.trim().toString(),
                        fragment_advance_search_etCity.text!!.trim().toString(),
                        fragment_advance_search_etCountry.text!!.trim().toString(),
                        city!![selectedStatePosition],
                        getTimeStampNew(
                            "1-" +
                                    selectedStartMonthPosition
                                    + "-" +
                                    year!![selectedStartYearPosition]
                        ),
                        getTimeStampNew(
                            "30-" +
                                    selectedEndMonthPosition
                                    + "-" +
                                    year!![selectedEndYearPosition]

                        ),
                        userId
                    )
                }
            }
        }
    }

    /**
     * This method is to check if the input firld are valid or not
     */
    private fun isValid(): Boolean {
        if (TextUtils.isEmpty(fragment_advance_search_etCompany.text!!.trim().toString())) {
            Utils.showSnackBar(
                activity!!, fragment_advance_search_etCountry, true,
                "Please enter company name."
            )

            return false
        } else if (selectedStatePosition == 0) {
            Utils.showSnackBar(
                activity!!, fragment_advance_search_etCountry, true,
                "Please select state."
            )

            return false
        } else if (selectedStartYearPosition == 0) {
            Utils.showSnackBar(
                activity!!, fragment_advance_search_etCountry, true,
                "Please select start year."
            )

            return false
        } else if (selectedEndYearPosition == 0) {
            Utils.showSnackBar(
                activity!!, fragment_advance_search_etCountry, true,
                "Please select end year."
            )

            return false
        } else if (selectedStartMonthPosition == 0) {
            Utils.showSnackBar(
                activity!!, fragment_advance_search_etCountry, true,
                "Please select start month."
            )

            return false
        } else if (selectedEndMonthPosition == 0) {
            Utils.showSnackBar(
                activity!!, fragment_advance_search_etCountry, true,
                "Please select end month."
            )
            return false
        } else if (TextUtils.isEmpty(fragment_advance_search_etCity.text!!.trim().toString())) {
            Utils.showSnackBar(
                activity!!, fragment_advance_search_etCountry, true,
                "Please enter city"
            )

            return false
        } else if (TextUtils.isEmpty(fragment_advance_search_etCountry.text!!.trim().toString())) {
            Utils.showSnackBar(
                activity!!, fragment_advance_search_etCountry, true,
                "Please enter country."
            )

            return false
        } else {
            return true
        }
    }

    /**
     * This method is to call the advance search api
     */
    private fun callAdvanceSearchApi(
        companyName: String,
        city: String,
        country: String,
        state: String,
        startDate: String,
        endDate: String,
        userId: String
    ) {
        val progressDialog = ProgressDialog(activity, R.style.ProgressDialog)

        if (!activity!!.isFinishing) {
            progressDialog.setCancelable(false)
            progressDialog.show()
            progressDialog.setContentView(R.layout.layout_progress_indicator)
        }

        WSUtils.getClient().advanceSearch(companyName, city, country, state, startDate, endDate, userId)
            .enqueue(object :
                Callback<ResponseBody> {

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                    progressDialog.dismiss()

                    val jsonObject = WSUtils.getResponseJSON(response)

                    if (jsonObject != null) {
                        val checkUserNameResponseModel = Gson().fromJson<DataModel.AdvanceSearchMainModel>(
                            jsonObject.toString(),
                            DataModel.AdvanceSearchMainModel::class.java!!
                        )
                        if (checkUserNameResponseModel.success != null && checkUserNameResponseModel.success == 1) {
                            Log.d("@@@", "Success")
                            goToSearchResultScreen(checkUserNameResponseModel)

                        } else {
                            Utils.showSnackBar(
                                activity!!, fragment_advance_search_etCountry, true,
                                checkUserNameResponseModel.message!!
                            )
                        }

                    } else {
                        Utils.showSnackBar(
                            activity!!,
                            fragment_advance_search_etCountry,
                            true,
                            getString(R.string.alert_some_error)
                        )
                    }

                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

                    progressDialog.dismiss()
                    Utils.showSnackBar(
                        activity!!,
                        fragment_advance_search_spCity,
                        true,
                        getString(R.string.alert_some_error)
                    )
                }
            })
    }

    /**
     * This method will return the timestamp
     */
    private fun getTimeStamp(selectedDate: String): String {
        val formatter: DateFormat
        formatter = SimpleDateFormat("dd/mm/yyyy", Locale.US)
        val date = formatter.parse(selectedDate) as Date
        val timeStampDate = Timestamp(date.time)
        return date.time.toString()
    }

    private fun getTimeStampNew(selectedDate: String): String {
        val str_date = "13-09-2011"
        val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.US)
        val date = formatter.parse(selectedDate) as Date
        System.out.println("Today is " + date.time)
        return date.time.toString()
    }

    /**
     * This method is to redirect user to search result screen
     */
    private fun goToSearchResultScreen(basicSearchMainModel: DataModel.AdvanceSearchMainModel) {
        val fragment = SearchResultFragment()
        val bundle = Bundle()
        bundle.putBoolean(Constants.IS_BASIC_SEARCH_RESULT_MODEL, false)
        bundle.putSerializable(Constants.BASIC_SEARCH_RESULT_MODEL, basicSearchMainModel)
        fragment.arguments = bundle
        replaceFragment(R.id.flContainer, fragment, false)

    }


}
