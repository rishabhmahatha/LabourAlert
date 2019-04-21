package com.labouralerts.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.labouralerts.R
import com.labouralerts.ui.model.DataModel

class GraphResultAdapter(
    private val context: Context,
    private val arrGraphList: ArrayList<DataModel.SearchCompanyCityState>
) : RecyclerView.Adapter<GraphResultAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var tvTitle: TextView? = itemView.findViewById(R.id.row_data_graph_tvTitle)
        var pbProgressBlue: ProgressBar? = itemView.findViewById(R.id.row_data_graph_pbProgressBlue)
        var pbProgressGray: ProgressBar? = itemView.findViewById(R.id.row_data_graph_pbProgressGray)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_data_graph, parent, false) as View
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: GraphResultAdapter.MyViewHolder, position: Int) {
        holder.tvTitle!!.text = arrGraphList[position].companyName
        Log.d("@======>", arrGraphList[position].employeesAffected.toString()!!)

        if ((holder.adapterPosition % 2) == 0) {
            holder.pbProgressGray!!.visibility = View.GONE
            holder.pbProgressBlue!!.visibility = View.VISIBLE
            if (arrGraphList[position].employeesAffected == null) {
                holder.pbProgressBlue!!.progress = 0
            } else {
                holder.pbProgressBlue!!.progress = arrGraphList[position].employeesAffected!!
            }
        } else {
            holder.pbProgressGray!!.visibility = View.VISIBLE
            holder.pbProgressBlue!!.visibility = View.GONE
            if (arrGraphList[position].employeesAffected == null) {
                holder.pbProgressGray!!.progress = 0
            } else {
                holder.pbProgressGray!!.progress = arrGraphList[position].employeesAffected!!
            }

        }
    }

    override fun getItemCount(): Int {
        return arrGraphList.size
    }


}