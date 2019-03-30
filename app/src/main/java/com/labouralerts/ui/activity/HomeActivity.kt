package com.labouralerts.ui.activity

import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.View
import com.labouralerts.R
import com.labouralerts.ui.fragment.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.layout_side_drawer.*
import kotlinx.android.synthetic.main.layout_toolbar.*


class HomeActivity : BaseActivity() {

    var mDrawerToggle: ActionBarDrawerToggle? = null

    override fun defineLayoutResource(): Int {
        return R.layout.activity_home
    }

    override fun initializeComponent() {
        setSupportActionBar(toolbar)

        mDrawerToggle =
            ActionBarDrawerToggle(this, drawer_layout, toolbar, com.labouralerts.R.string.app_name, R.string.app_name)

        mDrawerToggle!!.isDrawerIndicatorEnabled = false
        mDrawerToggle!!.setHomeAsUpIndicator(R.drawable.ic_side_menu_button_blue);

        mDrawerToggle!!.toolbarNavigationClickListener =
            View.OnClickListener { drawer_layout.openDrawer(GravityCompat.START) }


        mDrawerToggle!!.syncState()

        replaceFragment(R.id.flContainer, SearchResultFragment(), false)

        setOnClickListener()
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
        tvAccount.setOnClickListener(this)
        tvAboutUs.setOnClickListener(this)
        tvTermsConditions.setOnClickListener(this)
        tvLogOut.setOnClickListener(this)
    }

    /**
     * This method is to set the title in title bar
     */
    private fun setTitleBarTitle(title: String) {
        tvToolBarTitle.text = title
    }

    override fun onClick(view: View) {
        super.onClick(view)
        val id = view.id

        when (id) {

            R.id.tvSearch -> {
                closeDrawer()
                setTitleBarTitle(getString(R.string.menu_search))
                replaceFragment(R.id.flContainer, SearchFragment(), false)
            }


            R.id.tvAdvancedSearch -> {
                closeDrawer()
                setTitleBarTitle(getString(R.string.menu_advanced_search))
                replaceFragment(R.id.flContainer, AdvanceSearchFragment(), false)
            }


            R.id.tvTop10 -> {
                closeDrawer()
                setTitleBarTitle(getString(R.string.menu_top_10))
                replaceFragment(R.id.flContainer, Top10Fragment(), false)
            }


            R.id.tvAlertNotification -> {
                closeDrawer()
                setTitleBarTitle(getString(R.string.menu_alert_notification))
                replaceFragment(R.id.flContainer, NotificationFragment(), false)
            }


            R.id.tvAlertManager -> {
                closeDrawer()
                setTitleBarTitle(getString(R.string.menu_alert_manager))
                replaceFragment(R.id.flContainer, AlertManagerFragment(), false)
            }


            R.id.tvAccount -> {
                closeDrawer()
                setTitleBarTitle(getString(R.string.menu_account))
                replaceFragment(R.id.flContainer, AccountFragment(), false)
            }


            R.id.tvAboutUs -> {
                closeDrawer()
                setTitleBarTitle(getString(R.string.menu_about_us))

            }


            R.id.tvTermsConditions -> {
                closeDrawer()
                setTitleBarTitle(getString(R.string.menu_terms_and_conditions))
            }


            R.id.tvLogOut -> {
                closeDrawer()

            }
        }
    }

    /**
     * This method is to close the drawer
     */
    private fun closeDrawer() {
        drawer_layout.closeDrawer(GravityCompat.START)
    }

}