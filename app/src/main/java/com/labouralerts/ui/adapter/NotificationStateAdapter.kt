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

class NotificationStateAdapter(
    private val context: Context,
    private val arrNotificationList: ArrayList<DataModel.NotificationState>
) : RecyclerView.Adapter<NotificationStateAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var tvTitleFirst: TextView? = itemView.findViewById(R.id.ow_data_company_notification_tvTitleFirst)
        var ivNotificaiton: ImageView? = itemView.findViewById(R.id.ow_data_company_notification_ivNotificaiton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_data_company_notification, parent, false) as View
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationStateAdapter.MyViewHolder, position: Int) {
        holder.tvTitleFirst!!.text = arrNotificationList[position].name
        holder.ivNotificaiton!!.isSelected = arrNotificationList[position].status.equals("Active")

    }

    override fun getItemCount(): Int {
        return arrNotificationList.size
    }


}