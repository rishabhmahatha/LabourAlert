package com.labouralerts.webservice;

public class WSConstants {
    static final int WS_CONNECTION_TIMEOUT = 120;
    static final int WS_READ_TIMEOUT = 120;

    public static final int WS_STATUS_SUCCESS = 1;
    public static final int WS_STATUS_FAIL = 0;

    /**
     * Webservice url
     */
    static final String WS_BASE_API_URL = " http://www.laboralerts.com/laboralertapp/";

    static final String WS_METHOD_CHECK_USER_NAME = "checkUsername.php";
    static final String WS_METHOD_SIGN_UP = "signUp.php";
    static final String WS_METHOD_LOGIN = "login.php";
    static final String WS_METHOD_TOP_TEN= "topTen.php";
    static final String WS_METHOD_BASIC_SEARCH= "basicSearch.php";
    static final String WS_METHOD_ADVANCED_SEARCH= "advancedSearch.php";
    static final String WS_METHOD_CHANGE_PASSWORD= "changePassword.php";
    static final String WS_METHOD_SAVE_ALERT= "saveAlert.php";
    static final String WS_METHOD_DELETE_ALERT= "deleteAlert.php";
    static final String WS_METHOD_NOTIFICATION= "notification.php";
    static final String WS_METHOD_ALERT_NOTIFICATION= "alertNotifications.php";


    static final String WS_PARAM_USER_ID = "user_id";
    static final String WS_PARAM_USER_NAME = "user_name";
    static final String WS_PARAM_PASSWORD = "password";
    static final String WS_PARAM_DEVICE_ID = "device_id";
    static final String WS_PARAM_USER_EMAIL = "email";

    static final String WS_PARAM_SEARCH_FEILD = "search_field";
    static final String WS_PARAM_COMPANY_NAME = "company_name";
    static final String WS_PARAM_CITY = "city";
    static final String WS_PARAM_COUNTY = "county";
    static final String WS_PARAM_STATE = "state";
    static final String WS_PARAM_EFFECTIVE_DATE_START = "effective_date_start";
    static final String WS_PARAM_EFFECTIVE_DATE_END = "effective_date_end";
    static final String WS_PARAM_OLD_PASSWORD = "oldpwd";
    static final String WS_PARAM_NEW_PASSWORD = "newpwd";
    static final String WS_PARAM_BUSINESS_ID = "business_id";
    static final String WS_PARAM_STATUS = "status";
    static final String WS_PARAM_SERVICE = "service";
    static final String WS_PARAM_ADDRESS = "address1";
    static final String WS_PARAM_RENEW_DATE = "renew_date";
    static final String WS_PARAM_ZIP_CODE = "zip_code";
    static final String WS_PARAM_COUNTRY = "country";
    static final String WS_PARAM_PROFILE_STATUS = "profile_status";
    static final String WS_PARAM_TRANSACTION_ID = "transaction_id";
    static final String WS_PARAM_TRANSACTION_DATE = "transaction_date";
    static final String WS_PARAM_SERVICE_ID = "service_id";


}
