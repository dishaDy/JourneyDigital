package com.example.journeydigital.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.journeydigital.R
import com.example.journeydigital.constants.Const
import com.example.journeydigital.data.model.DashboardResponse
import com.example.journeydigital.databinding.RowDashboardPostBinding
import com.example.journeydigital.ui.`interface`.DashboardItemClickListener

internal class DashboardAdapter(private val context: Context,private val dashboardList: ArrayList<DashboardResponse>,
                                private val onClickListener: DashboardItemClickListener) : RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_dashboard_post, parent, false)



        return DashboardViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        with(holder) {
            // TODO
            binding.rowDashboardPostCvMain.setOnClickListener {
                onClickListener.dashboardItemClicked(
                    position,
                    Const.actionView
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return 10
    }

    internal inner class DashboardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = RowDashboardPostBinding.bind(view)

    }
}