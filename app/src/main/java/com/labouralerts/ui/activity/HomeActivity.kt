package com.labouralerts.ui.activity

import android.content.DialogInterface
import android.content.Intent
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.view.View
import com.google.android.gms.ads.MobileAds
import com.labouralerts.R
import com.labouralerts.ui.fragment.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.layout_side_drawer.*
import kotlinx.android.synthetic.main.layout_toolbar.*

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import android.util.Log
import com.labouralerts.utils.Constants
import com.labouralerts.utils.Preference

class HomeActivity : BaseActivity(), RewardedVideoAdListener {
    var mDrawerToggle: ActionBarDrawerToggle? = null
    var mAd: RewardedVideoAd? = null

    override fun defineLayoutResource(): Int {
        return R.layout.activity_home
    }

    override fun initializeComponent() {
        initAdMob()
        setSupportActionBar(toolbar)

        mDrawerToggle =
            ActionBarDrawerToggle(this, drawer_layout, toolbar, com.labouralerts.R.string.app_name, R.string.app_name)

        mDrawerToggle!!.isDrawerIndicatorEnabled = false
        mDrawerToggle!!.setHomeAsUpIndicator(R.drawable.ic_side_menu_button_blue);

        mDrawerToggle!!.toolbarNavigationClickListener =
            View.OnClickListener { drawer_layout.openDrawer(GravityCompat.START) }


        mDrawerToggle!!.syncState()
        manageToolbarVisibility(false)

        replaceFragment(R.id.flContainer, SearchOptionFragment(), false)

        setOnClickListener()
    }


    var adRequest: AdRequest? = null
    /*
    * This method is to initialize the ad mob*/
    private fun initAdMob() {
        adRequest = AdRequest.Builder().addTestDevice("3A95D6B3952C95EF94145A47C83B4A7F").build()
        adView.loadAd(adRequest)
    }


    /**
     * This method is to set the click listeners on all the view
     */
    private fun setOnClickListener() {
        tvSearch.setOnClickListener(this)
        tvAdvancedSearch.setOnClickListener(this)
        tvTop10.setOnClickListener(this)
        tvAlertNotification.setOnClickListener(this)
        tvAlertManager.setOnClickListener(this)
        tvUpgradeAccount.setOnClickListener(this)
        tvAccount.setOnClickListener(this)
        tvAboutUs.setOnClickListener(this)
        tvTermsConditions.setOnClickListener(this)
        tvLogOut.setOnClickListener(this)
        toolbar_btnGraph.setOnClickListener(this)
        toolbar_btnResult.setOnClickListener(this)
    }

    /**
     * This method is to set the title in title bar
     */
    fun setTitleBarTitle(title: String) {
        tvToolBarTitle.text = title
    }

    override fun onClick(view: View) {
        super.onClick(view)
        val id = view.id

        when (id) {

            R.id.tvSearch -> {
                closeDrawer()
                loadRewardedVideo()
                manageToolbarVisibility(false)
                //  setTitleBarTitle(getString(R.string.menu_search))
                replaceFragment(R.id.flContainer, SearchOptionFragment(), false)
            }


            R.id.tvAdvancedSearch -> {
                closeDrawer()
                loadRewardedVideo()
                manageToolbarVisibility(false)
                setTitleBarTitle(getString(R.string.menu_advanced_search))
                replaceFragment(R.id.flContainer, AdvanceSearchFragment(), false)
            }


            R.id.tvTop10 -> {
                closeDrawer()
                loadRewardedVideo()
                manageToolbarVisibility(false)
                setTitleBarTitle(getString(R.string.title_top_10))
                replaceFragment(R.id.flContainer, Top10Fragment(), false)
            }


            R.id.tvAlertNotification -> {
                closeDrawer()
                loadRewardedVideo()
                manageToolbarVisibility(false)
                setTitleBarTitle(getString(R.string.menu_alert_notification))
                replaceFragment(R.id.flContainer, NotificationFragment(), false)
            }


            R.id.tvAlertManager -> {
                closeDrawer()
                loadRewardedVideo()
                manageToolbarVisibility(false)
                setTitleBarTitle(getString(R.string.menu_alert_manager))
                replaceFragment(R.id.flContainer, AlertManagerFragment(), false)
            }


            R.id.tvAccount -> {
                closeDrawer()
                loadRewardedVideo()
                manageToolbarVisibility(false)
                setTitleBarTitle(getString(R.string.menu_account))
                replaceFragment(R.id.flContainer, AccountFragment(), false)
            }

            R.id.tvUpgradeAccount -> {
                closeDrawer()
                loadRewardedVideo()
                manageToolbarVisibility(false)
                setTitleBarTitle(getString(R.string.menu_upgrade_account))
                replaceFragment(R.id.flContainer, UpgradeAccountFragment(), false)
            }

            R.id.tvAboutUs -> {
                closeDrawer()
                loadRewardedVideo()
                val intent = Intent(this, TcPrivacyAboutUsActivity::class.java);
                intent.putExtra(Constants.TITLE, "About Us")
                startActivity(intent)
            }


            R.id.tvTermsConditions -> {
                closeDrawer()
                loadRewardedVideo()
                val intent = Intent(this, TcPrivacyAboutUsActivity::class.java);
                intent.putExtra(Constants.TITLE, "Terms and Conditions")
                startActivity(intent)
            }


            R.id.tvLogOut -> {
                closeDrawer()
                showLogOutAlert()
            }

            R.id.toolbar_btnGraph -> {
                val fragment = supportFragmentManager.findFragmentById(R.id.flContainer)
                if (fragment is SearchResultFragment) {
                    fragment.manageGraphResultListView(true)
                    toolbar_btnGraph.isSelected = true
                    toolbar_btnResult.isSelected = false
                }
            }

            R.id.toolbar_btnResult -> {
                val fragment = supportFragmentManager.findFragmentById(R.id.flContainer)
                if (fragment is SearchResultFragment) {
                    fragment.manageGraphResultListView(false)
                    toolbar_btnGraph.isSelected = false
                    toolbar_btnResult.isSelected = true
                }
            }


            R.id.toolbar_btnShare -> {

            }
        }
    }

    /**
     * This method is to close the drawer
     */
    private fun closeDrawer() {
        drawer_layout.closeDrawer(GravityCompat.START)
    }

    /**
     * This method is to manage the show hide of the toolbar
     */
    fun manageToolbarVisibility(isResultfragment: Boolean) {
        if (isResultfragment) {
            toolbar_btnGraph.isSelected = false
            toolbar_btnResult.isSelected = true
            llToolbarCommon.visibility = View.GONE
            llToolbarResult.visibility = View.VISIBLE
        } else {
            llToolbarCommon.visibility = View.VISIBLE
            llToolbarResult.visibility = View.GONE
        }
    }

    override fun onPause() {
        super.onPause()
        if (adView != null) {
            adView.pause()
        }
    }

    override fun onResume() {
        super.onResume()
        if (adView != null) {
            adView.resume()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (adView != null) {
            adView.destroy()
        }
    }

    /**
     * This method is to show the alert for logout
     */
    private fun showLogOutAlert() {
        // build alert dialog
        val dialogBuilder = AlertDialog.Builder(this)

        // set message of alert dialog
        dialogBuilder.setMessage("Do you want to Logout ?")
            // if the dialog is cancelable
            .setCancelable(false)
            // positive button text and action
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->
                Preference.instance.clearAllPreferenceData()
                val intent = Intent(this, LoginSignUpActivity::class.java)
                startActivity(intent)
                this.finish()
            })
            // negative button text and action
            .setNegativeButton("No", DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })

        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle("Alert Logout")
        // show alert dialog
        alert.show()
    }


    private fun loadRewardedVideo() {
        mAd = MobileAds.getRewardedVideoAdInstance(this)
        mAd!!.rewardedVideoAdListener = this
        mAd!!.loadAd(
            getString(com.labouralerts.R.string.interstitial_full_screen),
            AdRequest.Builder().addTestDevice("3A95D6B3952C95EF94145A47C83B4A7F")
                .build()
        )
    }

    override fun onRewardedVideoAdLoaded() {
        Log.i("Video Ad", "Rewarded: onRewardedVideoAdLoaded")
        try {
            if (mAd!!.isLoaded()) {
                mAd!!.show()
            }
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }

    }

    override fun onRewardedVideoAdOpened() {
        Log.i("Video Ad", "Rewarded: onRewardedVideoAdOpened")
    }

    override fun onRewardedVideoStarted() {
        Log.i("Video Ad", "Rewarded: onRewardedVideoStarted")
    }

    override fun onRewardedVideoAdClosed() {
        Log.i("Video Ad", "Rewarded: onRewardedVideoAdClosed")
    }

    override fun onRewarded(rewardItem: RewardItem) {
        Log.i(
            "Video Ad", "Rewarded:  onRewarded! currency: " + rewardItem.type + "  amount: " +
                    rewardItem.amount
        )

    }

    override fun onRewardedVideoAdLeftApplication() {
        Log.i("Video Ad", "Rewarded: onRewardedVideoAdLeftApplication ")
    }

    override fun onRewardedVideoAdFailedToLoad(i: Int) {
        Log.i("Video Ad", "Rewarded: onRewardedVideoAdFailedToLoad: $i")
    }

    override fun onRewardedVideoCompleted() {
        Log.i("Video Ad", "Rewarded: onRewardedVideoCompleted")
    }
}