package com.labouralerts.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.labouralerts.R
import com.labouralerts.ui.model.DataModel

class ResultsAdapter(
    private val context: Context,
    private val onItemClick: OnItemClick,
    private val arrNotificationList: ArrayList<DataModel.SearchCompanyCityState>
) : RecyclerView.Adapter<ResultsAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var tvTitleFirst: TextView? = itemView.findViewById(R.id.row_data_result_tvTitleFirst)
        var tvTitleSecond: TextView? = itemView.findViewById(R.id.row_data_result_tvTitleSecond)
        var tvTitleThird: TextView? = itemView.findViewById(R.id.row_data_result_tvTitleThird)
        var tvTitleFirstCount: TextView? = itemView.findViewById(R.id.row_data_result_tvTitleFirstCount)
        var ivNotificaiton: ImageView? = itemView.findViewById(R.id.row_data_result_ivNotificaiton)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_data_results, parent, false) as View
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResultsAdapter.MyViewHolder, position: Int) {
        holder.tvTitleFirst!!.text = arrNotificationList[position].companyName
        holder.tvTitleSecond!!.text = arrNotificationList[position].state
        holder.tvTitleThird!!.text = arrNotificationList[position].city1
        holder.tvTitleFirstCount!!.text = arrNotificationList[position].employeesAffected.toString()
        holder.ivNotificaiton!!.isSelected = arrNotificationList[position].alertStatus.equals("Active")
        holder.ivNotificaiton!!.setOnClickListener { p0 -> onItemClick.onSaveAlertClick(position, p0) }
    }

    override fun getItemCount(): Int {
        return arrNotificationList.size
    }

    interface OnItemClick {
        fun onSaveAlertClick(position: Int, view: View)
    }
}