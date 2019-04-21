package com.labouralerts.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.labouralerts.R
import com.labouralerts.ui.model.DataModel

class NotificationAdapter(
    private val context: Context,
    private val arrNotificationList: ArrayList<DataModel.NotificationModel>
) : RecyclerView.Adapter<NotificationAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var tvTitleFirst: TextView? = itemView.findViewById(R.id.ow_data_notification_tvTitleFirst)
        var tvTitleSecond: TextView? = itemView.findViewById(R.id.ow_data_notification_tvTitleSecond)
        var tvTitleSecondCount: TextView? = itemView.findViewById(R.id.ow_data_notification_tvTitleSecondCount)
        var tvTitleThird: TextView? = itemView.findViewById(R.id.ow_data_notification_tvTitleThird)
        var tvTitleFourth: TextView? = itemView.findViewById(R.id.ow_data_notification_tvTitleFourth)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_data_notification, parent, false) as View
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationAdapter.MyViewHolder, position: Int) {
        holder.tvTitleFirst!!.text = arrNotificationList[position].firstTitle
        holder.tvTitleSecond!!.text = arrNotificationList[position].secondTitle
        holder.tvTitleSecondCount!!.text = arrNotificationList[position].secondTitleCount
        holder.tvTitleThird!!.text = arrNotificationList[position].thirdTitle
        holder.tvTitleFourth!!.text = arrNotificationList[position].fourthTitle
    }

    override fun getItemCount(): Int {
        return arrNotificationList.size
    }


}