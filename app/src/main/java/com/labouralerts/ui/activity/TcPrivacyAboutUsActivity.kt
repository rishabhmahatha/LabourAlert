package com.labouralerts.ui.activity

import android.view.View
import android.widget.Toast
import com.labouralerts.R
import com.labouralerts.utils.Constants
import kotlinx.android.synthetic.main.activity_tcprivacyaboutus.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


class TcPrivacyAboutUsActivity : BaseActivity() {
    private var text = StringBuilder()

    override fun defineLayoutResource(): Int {
        return R.layout.activity_tcprivacyaboutus
    }

    override fun initializeComponent() {
        activity_tc_about_pp_ivClose.setOnClickListener(this)

        if (intent != null && intent.hasExtra(Constants.TITLE)) {
            activity_tc_about_pp_tvToolBarTitle.text = intent.getStringExtra(Constants.TITLE)
            if (intent.getStringExtra(Constants.TITLE).equals("Privacy Policy")) {
                activity_tc_about_pp_tvContent.text = getString(R.string.pp)
            } else if (intent.getStringExtra(Constants.TITLE).equals("About Us")) {
                activity_tc_about_pp_tvContent.text = getString(R.string.about_us)
            } else {
                activity_tc_about_pp_tvContent.text = getString(R.string.tc)
            }
        }

    }

    override fun onClick(view: View) {
        super.onClick(view)
        val id = view.id
        when (id) {
            activity_tc_about_pp_ivClose.id -> {
                this.finish()
            }
        }
    }

}