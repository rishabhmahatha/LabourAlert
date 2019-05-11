package com.labouralerts.ui.fragment

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import com.android.billingclient.api.*
import com.labouralerts.R
import com.labouralerts.utils.Constants
import com.labouralerts.utils.Preference
import kotlinx.android.synthetic.main.fragment_subscription.*

class UpgradeAccountFragment : BaseFragments(), PurchasesUpdatedListener {
    private lateinit var billingClient: BillingClient
    private lateinit var subscriptionType: String

    override fun defineLayoutResource(): Int {
        return R.layout.fragment_subscription
    }

    override fun initializeComponent(view: View) {
        fragment_subscription_tvFreeTrial.setOnClickListener(this)
        fragment_subscription_tv6Months.setOnClickListener(this)
        fragment_subscription_tvOneYear.setOnClickListener(this)

    }

    override fun onClick(view: View) {
        super.onClick(view)

        when (view.id) {
            fragment_subscription_tvFreeTrial.id -> {

            }

            fragment_subscription_tv6Months.id -> {
                subscriptionType = Constants._6_MONTH_SUBSCRIPTION_KEY
                getConnectionStatus(activity!!, this, Constants._6_MONTH_SUBSCRIPTION_KEY)
            }

            fragment_subscription_tvOneYear.id -> {
                subscriptionType = Constants._12_MONTH_SUBSCRIPTION_KEY
                getConnectionStatus(activity!!, this, Constants._12_MONTH_SUBSCRIPTION_KEY)
            }
        }
    }


    /**
     * This method is to launch the subscription dialog
     */
    private fun getConnectionStatus(
        context: Context,
        purchasesUpdatedListener: PurchasesUpdatedListener,
        subscriptionId: String
    ) {
        billingClient = BillingClient.newBuilder(context).setListener(purchasesUpdatedListener).build()
        billingClient.startConnection(
            object : BillingClientStateListener {
                override fun onBillingSetupFinished(@BillingClient.BillingResponse billingResponseCode: Int) {
                    if (billingResponseCode == BillingClient.BillingResponse.OK) {
                        // The billing client is ready. You can query purchases here.
                        openSubscriptionDialog(activity!!, subscriptionId)
                    }
                }

                override fun onBillingServiceDisconnected() {
                    // Try to restart the connection on the next request to
                    // Google Play by calling the startConnection() method.
                }
            })
    }


    private fun openSubscriptionDialog(activity: Activity, subscriptionId: String) {
        val flowParams = BillingFlowParams.newBuilder()
            .setSku(subscriptionId)
            .setType(BillingClient.SkuType.SUBS) // SkuType.SUB for subscription
            .build()
        val responseCode = billingClient.launchBillingFlow(activity, flowParams)
    }

    override fun onPurchasesUpdated(responseCode: Int, purchases: MutableList<Purchase>?) {
        if (responseCode == BillingClient.BillingResponse.OK && purchases != null) {
            for (purchase in purchases) {

                if (subscriptionType.equals(Constants._6_MONTH_SUBSCRIPTION_KEY)) {
                    Preference.instance.setIntData(Preference.instance.PREF_SUBSCRIPTION_TYPE, 1)
                } else if (subscriptionType.equals(Constants._12_MONTH_SUBSCRIPTION_KEY)) {
                    Preference.instance.setIntData(Preference.instance.PREF_SUBSCRIPTION_TYPE, 2)
                } else {
                    Preference.instance.setIntData(Preference.instance.PREF_SUBSCRIPTION_TYPE, 0)
                }
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