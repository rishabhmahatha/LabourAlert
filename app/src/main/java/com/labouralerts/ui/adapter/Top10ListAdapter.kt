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

class Top10ListAdapter(
    private val context: Context,
    private val onItemClick: OnItemClick,
    private val arrTop10List: ArrayList<DataModel._1>
) : RecyclerView.Adapter<Top10ListAdapter.MyViewHolder>() {


    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var tvSNo: TextView? = itemView.findViewById(R.id.row_data_top10_tvSNo)
        var tvName: TextView? = itemView.findViewById(R.id.row_data_top10_tvName)
        var tvNumber: TextView? = itemView.findViewById(R.id.row_data_top10_tvNumber)
        var ivNotificationIcon: ImageView? = itemView.findViewById(R.id.row_data_top10_ivNotificationIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_top_10, parent, false) as View
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: Top10ListAdapter.MyViewHolder, position: Int) {
        holder.tvSNo!!.text = (position + 1).toString()
        holder.tvName!!.text = arrTop10List[position].companyName
        holder.tvNumber!!.text = arrTop10List[position].affectedEmployees

        holder.ivNotificationIcon!!.isSelected = arrTop10List[position].alertStatus.equals("Active")

        holder.ivNotificationIcon!!.setOnClickListener { p0 -> onItemClick.onSaveAlertClick(position, p0) }
    }

    override fun getItemCount(): Int {
        return arrTop10List.size
    }


    interface OnItemClick {
        fun onSaveAlertClick(position: Int, view: View)
    }
}