package com.labouralerts.ui.fragment

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import com.android.billingclient.api.*
import com.labouralerts.R

class UpgradeAccountFragment:BaseFragments(),PurchasesUpdatedListener{
    private lateinit var billingClient: BillingClient


    override fun defineLayoutResource(): Int {
        return R.layout.fragment_subscription
    }

    override fun initializeComponent(view: View) {
//        getConnectionStatus(activity!!, this)
    }


    /**
     * This method is to launch the subscription dialog
     */
    private fun getConnectionStatus(context: Context, purchasesUpdatedListener: PurchasesUpdatedListener) {
        billingClient = BillingClient.newBuilder(context).setListener(purchasesUpdatedListener).build()
        billingClient.startConnection(
            object : BillingClientStateListener {
                override fun onBillingSetupFinished(@BillingClient.BillingResponse billingResponseCode: Int) {
                    if (billingResponseCode == BillingClient.BillingResponse.OK) {
                        // The billing client is ready. You can query purchases here.
                        openSubscriptionDialog(activity!!)
                    }
                }

                override fun onBillingServiceDisconnected() {
                    // Try to restart the connection on the next request to
                    // Google Play by calling the startConnection() method.
                }
            })
    }


    private fun openSubscriptionDialog(activity: Activity) {
        val flowParams = BillingFlowParams.newBuilder()
//            .setSku(Constants.SUBS_MONTHLY_SUBSCRIPTION)
            .setSku("SUBSCRIPTION_ID")
            .setType(BillingClient.SkuType.SUBS) // SkuType.SUB for subscription
            .build()
        val responseCode = billingClient.launchBillingFlow(activity, flowParams)
    }

    override fun onPurchasesUpdated(responseCode: Int, purchases: MutableList<Purchase>?) {
        if (responseCode == BillingClient.BillingResponse.OK && purchases != null) {
            for (purchase in purchases) {
//                callAddSubscriptionApi(purchase.orderId, purchase.isAutoRenewing.toString(), WsConstants.DEVICE_OS_ANDROID, MyTrax.getInstance()!!.packageName,
//                    Constants.SUBS_MONTHLY_SUBSCRIPTION, purchase.purchaseTime.toString(), purchase.purchaseToken, purchase.signature
//                )

            }
        } else if (responseCode == BillingClient.BillingResponse.USER_CANCELED) {
            // Handle an error caused by a user cancelling the purchase flow.
            Log.d("@@@@@", "You have cancelled the subscription process")
//            Utils.showSnackBar(context, "You have cancelled the subscription process", true)
        } else {
            // Handle any other error codes.
            Log.d("@@@@@", "Purchased Error")
        }
    }
}