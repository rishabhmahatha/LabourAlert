package com.labouralerts.ui.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class DataModel {

    //Login response model
    class LoginResponseModel {
        @SerializedName("Success")
        @Expose
        var Success: Int? = null
        @SerializedName("Failure")
        @Expose
        var Failure: Int? = null
        @SerializedName("message")
        @Expose
        var message: String? = null
        @SerializedName("user_id")
        @Expose
        var user_id: String? = null
        @SerializedName("renew_date")
        @Expose
        var renew_date: String? = null
        @SerializedName("first_name")
        @Expose
        var first_name: String? = null
        @SerializedName("last_name")
        @Expose
        var last_name: String? = null
        @SerializedName("service_id")
        @Expose
        var service_id: Int? = null
        @SerializedName("account_type")
        @Expose
        var account_type: String? = null

    }

    //check user name response model
    class CheckUserNameResponseModel {
        @SerializedName("Success")
        @Expose
        var Success: Int? = null
        @SerializedName("Failure")
        @Expose
        var Failure: Int? = null
        @SerializedName("message")
        @Expose
        var message: String? = null

    }

    //Sign Up response model
    class SignUpResponseModel {
        @SerializedName("Success")
        @Expose
        var Success: Int? = null
        @SerializedName("Failure")
        @Expose
        var Failure: Int? = null
        @SerializedName("message")
        @Expose
        var message: String? = null
        @SerializedName("account_type")
        @Expose
        var account_type: String? = null
        @SerializedName("userid")
        @Expose
        var userid: String? = null
        @SerializedName("renew_date")
        @Expose
        var renew_date: String? = null

    }

    //change pass response model
    class ChangePasswordResponseModel {
        @SerializedName("Success")
        @Expose
        var Success: Int? = null
        @SerializedName("Failure")
        @Expose
        var Failure: Int? = null
        @SerializedName("message")
        @Expose
        var message: String? = null

    }

    //basic search response model
    class BasicSearchResponseModel {
        @SerializedName("Success")
        @Expose
        var Success: Int? = null
        @SerializedName("Failure")
        @Expose
        var Failure: Int? = null
        @SerializedName("message")
        @Expose
        var message: String? = null

    }

    //Top10 response model
    inner class Company {

        @SerializedName("1")
        @Expose
        var _1: ArrayList<_1>? = null
        @SerializedName("2")
        @Expose
        var _2: ArrayList<_1>? = null
        @SerializedName("3")
        @Expose
        var _3: ArrayList<_1>? = null
        @SerializedName("12")
        @Expose
        var _12: ArrayList<_1>? = null

    }

    class Top10Result {

        @SerializedName("company")
        @Expose
        var company: Company? = null
        @SerializedName("state")
        @Expose
        var state: State? = null
        @SerializedName("Success")
        @Expose
        var success: Int? = null
        @SerializedName("Failure")
        @Expose
        var Failure: Int? = null
        @SerializedName("message")
        @Expose
        var message: String? = null

    }

    inner class State {

        @SerializedName("1")
        @Expose
        var _1: ArrayList<_1_>? = null
        @SerializedName("2")
        @Expose
        var _2: ArrayList<_1_>? = null
        @SerializedName("3")
        @Expose
        var _3: ArrayList<_1_>? = null
        @SerializedName("12")
        @Expose
        var _12: ArrayList<_1_>? = null

    }

    class _1 {

        @SerializedName("company_name")
        @Expose
        var companyName: String? = null
        @SerializedName("affected_employees")
        @Expose
        var affectedEmployees: String? = null
        @SerializedName("business_id")
        @Expose
        var businessId: String? = null
        @SerializedName("alert_status")
        @Expose
        var alertStatus: String? = null

    }

    class _1_ {

        @SerializedName("state")
        @Expose
        var state: String? = null
        @SerializedName("affected_employees")
        @Expose
        var affectedEmployees: String? = null
        @SerializedName("state_id")
        @Expose
        var stateId: Int? = null
        @SerializedName("alert_status")
        @Expose
        var alertStatus: String? = null
        @SerializedName("company_name")
        @Expose
        var companyName: String? = null
        @SerializedName("business_id")
        @Expose
        var businessId: String? = null

    }


    //Basic Search model
    class SearchCompanyCityState : Serializable {

        @SerializedName("year")
        @Expose
        var year: String? = null
        @SerializedName("business_id")
        @Expose
        var businessId: Int? = null
        @SerializedName("notice_received")
        @Expose
        var noticeReceived: String? = null
        @SerializedName("company_name")
        @Expose
        var companyName: String? = null
        @SerializedName("employees_affected")
        @Expose
        var employeesAffected: Int? = null
        @SerializedName("event_type")
        @Expose
        var eventType: String? = null
        @SerializedName("effective_date_start")
        @Expose
        var effectiveDateStart: String? = null
        @SerializedName("effective_date_end")
        @Expose
        var effectiveDateEnd: String? = null
        @SerializedName("city1")
        @Expose
        var city1: String? = null
        @SerializedName("city2")
        @Expose
        var city2: String? = null
        @SerializedName("city3")
        @Expose
        var city3: String? = null
        @SerializedName("city4")
        @Expose
        var city4: String? = null
        @SerializedName("city5")
        @Expose
        var city5: String? = null
        @SerializedName("country")
        @Expose
        var country: String? = null
        @SerializedName("state")
        @Expose
        var state: String? = null
        @SerializedName("upload_date")
        @Expose
        var uploadDate: String? = null
        @SerializedName("alert_status")
        @Expose
        var alertStatus: String? = null

    }

    class BasicSearch : Serializable {

        @SerializedName("company")
        @Expose
        var company: ArrayList<SearchCompanyCityState>? = null
        @SerializedName("city")
        @Expose
        var city: ArrayList<SearchCompanyCityState>? = null
        @SerializedName("state")
        @Expose
        var state: ArrayList<SearchCompanyCityState>? = null

    }

    inner class BasicSearchMainModel : Serializable {

        @SerializedName("data")
        @Expose
        var data: BasicSearch? = null
        @SerializedName("Success")
        @Expose
        var success: Int? = null
        @SerializedName("Failure")
        @Expose
        var Failure: Int? = null
        @SerializedName("message")
        @Expose
        var message: String? = null

    }

    //Notification model
    class NotificationCompany {

        @SerializedName("alert_id")
        @Expose
        var alertId: Int? = null
        @SerializedName("user_id")
        @Expose
        var userId: Int? = null
        @SerializedName("business_id")
        @Expose
        var businessId: Int? = null
        @SerializedName("status")
        @Expose
        var status: String? = null
        @SerializedName("company_name")
        @Expose
        var companyName: String? = null
        @SerializedName("Name")
        @Expose
        var name: String? = null

    }

    class Notification {

        @SerializedName("city")
        @Expose
        var city: String? = null
        @SerializedName("company")
        @Expose
        var company: List<NotificationCompany>? = null
        @SerializedName("state")
        @Expose
        var state: List<NotificationState>? = null
        @SerializedName("Success")
        @Expose
        var success: Int? = null
        @SerializedName("message")
        @Expose
        var message: String? = null
        @SerializedName("topTenCompany")
        @Expose
        var topTenCompany: List<Any>? = null
        @SerializedName("topTenState")
        @Expose
        var topTenState: List<Any>? = null

    }

    inner class NotificationState {

        @SerializedName("alert_id")
        @Expose
        var alertId: String? = null
        @SerializedName("user_id")
        @Expose
        var userId: Int? = null
        @SerializedName("state_id")
        @Expose
        var stateId: Int? = null
        @SerializedName("status")
        @Expose
        var status: String? = null
        @SerializedName("Name")
        @Expose
        var name: String? = null
        @SerializedName("company_name")
        @Expose
        var companyName: String? = null

    }
//    -----------------------------------------------------------------------------------------------

    //Notification List model
    class NotificationModel {
        var firstTitle = ""
        var secondTitle = ""
        var secondTitleCount = ""
        var thirdTitle = ""
        var fourthTitle = ""
    }

    //Result List model
    class ResultModel {
        var firstTitle = ""
        var firstTitleCount = ""
        var secondTitle = ""
        var thirdTitle = ""
    }


    //Result List model
    class GraphResultModel {
        var title = ""
        var progress = 0
    }

    //Result List model
    class Top10Model {
        var sNo = ""
        var name = ""
        var number = ""
    }
}