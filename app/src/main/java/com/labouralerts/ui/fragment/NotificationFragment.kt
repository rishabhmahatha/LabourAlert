package com.labouralerts.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.labouralerts.R
import com.labouralerts.ui.adapter.NotificationAdapter
import com.labouralerts.ui.model.DataModel
import kotlinx.android.synthetic.main.fragment_notification.*

class NotificationFragment : BaseFragments() {
    override fun defineLayoutResource(): Int {
        return R.layout.fragment_notification
    }

    override fun initializeComponent(view: View) {
        val viewManager = LinearLayoutManager(activity)
        val notificationListAdapter = NotificationAdapter(activity!!, getDummyNotificationData())
        fragment_notification_rvNotificationList.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = notificationListAdapter
        }
    }

    /**
     * This method will return the dummy notification data
     */

    private fun getDummyNotificationData(): ArrayList<DataModel.NotificationModel> {
        val arrNotificationList = ArrayList<DataModel.NotificationModel>()

        for (i in 1..10) {
            val item = DataModel.NotificationModel()
            item.firstTitle = "KapStone Container Corporation"
            item.secondTitle = "Jobs Imapacted : "
            item.secondTitleCount = "67"
            item.thirdTitle = "Oakland"
            item.fourthTitle = "CA"
            arrNotificationList.add(item)
        }

        return arrNotificationList;

    }

}