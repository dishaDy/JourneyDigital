package com.example.journeydigital.ui.adapter

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.journeydigital.R
import com.example.journeydigital.constants.Const
import com.example.journeydigital.data.model.DashboardResponse
import com.example.journeydigital.databinding.RowDashboardPostBinding
import com.example.journeydigital.extensions.makeGone
import com.example.journeydigital.extensions.makeVisible
import com.example.journeydigital.ui.`interface`.DashboardItemClickListener

class DashboardAdapter(
    private val context: Context, private val dashboardList: ArrayList<DashboardResponse>,
    private val onClickListener: DashboardItemClickListener
) : RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder>(),
    Filterable {
    private var filterList: ArrayList<DashboardResponse> = dashboardList

    init {
        filterList = dashboardList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_dashboard_post, parent, false)
        return DashboardViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        with(holder) {

            if (!TextUtils.isEmpty(filterList[position].title)) {
                binding.rowDashboardTvTitle.makeVisible()
                binding.rowDashboardTvTitle.text = filterList[position].title
            } else {
                binding.rowDashboardTvTitle.makeGone()
            }

            if (!TextUtils.isEmpty(filterList[position].body)) {
                binding.rowDashboardTvBody.makeVisible()
                binding.rowDashboardTvBody.text = filterList[position].body
            } else {
                binding.rowDashboardTvBody.makeGone()
            }

            binding.rowDashboardPostCvMain.setOnClickListener {
                onClickListener.dashboardItemClicked(
                    position,
                    Const.actionView
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return filterList.size
    }

    inner class DashboardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = RowDashboardPostBinding.bind(view)

    }
    /**
     * Filters query result data
     */
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                filterList = if (charSearch.isEmpty()) {
                    dashboardList
                } else {
                    val resultList = ArrayList<DashboardResponse>()
                    for (row in dashboardList) {
                        if (row.title.lowercase().contains(constraint.toString().lowercase())||row.body.lowercase().contains(constraint.toString().lowercase())) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterList = results?.values as ArrayList<DashboardResponse>
                notifyDataSetChanged()
            }
        }
    }
}