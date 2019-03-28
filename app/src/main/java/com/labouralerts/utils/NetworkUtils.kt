package com.labouralerts.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import com.labouralerts.R


/**
 * Purpose of this Class is to check internet connection of phone and perform actions on user's input
 */
object NetworkUtils {

    /**
     * Checks internet network connection.
     *
     * @param context    Activity context
     * @param message    if want to show connection message to user then true, false otherwise.
     * @param isSnackBar if want to show snackBar then true else shows alert dialog with buttons.
     * @return if network connectivity exists or is in the process of being established, false otherwise.
     */
    fun isOnline(context: Activity?, message: Boolean, isSnackBar: Boolean): Boolean {

        if (context != null && !context.isFinishing) {
            if (isNetworkAvailable(context)) {
                return true
            }

            if (message) {
                if (isSnackBar) {

                    Utils.showSnackBar(
                        context,
                        context.findViewById<View>(R.id.content),
                        true,
                        context.getString(R.string.alert_no_internet)
                    )

                } else {
                    val dialog = AlertDialog.Builder(context)

                    dialog.setTitle(context.getString(R.string.app_name))
                    dialog.setCancelable(false)
                    dialog.setMessage(context.getString(R.string.alert_no_internet))

                    dialog.setNeutralButton(context.getString(R.string.ok)) { dialog, id -> dialog.dismiss() }
                    dialog.show()
                }

            }
        }
        return false
    }

    /**
     * Checks the Network availability.
     *
     * @return true if internet available, false otherwise
     */
    fun isNetworkAvailable(context: Context): Boolean {

        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}
