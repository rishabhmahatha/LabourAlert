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
import com.google.android.gms.ads.reward.RewardedVideoAd


class HomeActivity : BaseActivity() {

    var mDrawerToggle: ActionBarDrawerToggle? = null
    var mRewardedVideoAd: RewardedVideoAd? = null

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

    /*
    * This method is to initialize the ad mob*/
    private fun initAdMob() {
        // initialize the AdMob app
        MobileAds.initialize(this, getString(R.string.admob_app_id))

        val adRequest = AdRequest.Builder()
            .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
            // Check the LogCat to get your test device ID
            .addTestDevice("3A95D6B3952C95EF94145A47C83B4A7F")
            .build()

        adView.loadAd(adRequest)
    }

    /*This method is to initialize the reward video ad*/
    private fun initVideoAd() {
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);

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
    public fun setTitleBarTitle(title: String) {
        tvToolBarTitle.text = title
    }

    override fun onClick(view: View) {
        super.onClick(view)
        val id = view.id

        when (id) {

            R.id.tvSearch -> {
                closeDrawer()
                manageToolbarVisibility(false)
                //  setTitleBarTitle(getString(R.string.menu_search))
                replaceFragment(R.id.flContainer, SearchOptionFragment(), false)
            }


            R.id.tvAdvancedSearch -> {
                closeDrawer()
                manageToolbarVisibility(false)
                setTitleBarTitle(getString(R.string.menu_advanced_search))
                replaceFragment(R.id.flContainer, AdvanceSearchFragment(), false)
            }


            R.id.tvTop10 -> {
                closeDrawer()
                manageToolbarVisibility(false)
                setTitleBarTitle(getString(R.string.title_top_10))
                replaceFragment(R.id.flContainer, Top10Fragment(), false)
            }


            R.id.tvAlertNotification -> {
                closeDrawer()
                manageToolbarVisibility(false)
                setTitleBarTitle(getString(R.string.menu_alert_notification))
                replaceFragment(R.id.flContainer, NotificationFragment(), false)
            }


            R.id.tvAlertManager -> {
                closeDrawer()
                manageToolbarVisibility(false)
                setTitleBarTitle(getString(R.string.menu_alert_manager))
                replaceFragment(R.id.flContainer, AlertManagerFragment(), false)
            }


            R.id.tvAccount -> {
                closeDrawer()
                manageToolbarVisibility(false)
                setTitleBarTitle(getString(R.string.menu_account))
                replaceFragment(R.id.flContainer, AccountFragment(), false)
            }

            R.id.tvUpgradeAccount -> {
                closeDrawer()
                manageToolbarVisibility(false)
                setTitleBarTitle(getString(R.string.menu_upgrade_account))
                replaceFragment(R.id.flContainer, UpgradeAccountFragment(), false)
            }

            R.id.tvAboutUs -> {
                closeDrawer()
                manageToolbarVisibility(false)
                setTitleBarTitle(getString(R.string.menu_about_us))
                replaceFragment(R.id.flContainer, AboutUsFragment(), false)

            }


            R.id.tvTermsConditions -> {
                closeDrawer()
                manageToolbarVisibility(false)
                setTitleBarTitle(getString(R.string.menu_terms_and_conditions))
                replaceFragment(R.id.flContainer, WebViewFragment(), false)
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

}