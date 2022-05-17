package com.example.journeydigital.ui.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.journeydigital.data.model.DashboardResponse
import com.example.journeydigital.databinding.FrgDashboardBinding
import com.example.journeydigital.extensions.isNetworkStatusAvailable
import com.example.journeydigital.extensions.observeOnce
import com.example.journeydigital.ui.ViewModelFactory.DashboardViewModelFactory
import com.example.journeydigital.ui.`interface`.DashboardItemClickListener
import com.example.journeydigital.ui.adapter.DashboardAdapter
import com.example.journeydigital.ui.view.activity.AppMainActivity
import com.example.journeydigital.ui.viewmodel.DashboardViewModel

class DashboardFragment : Fragment() {
    private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FrgDashboardBinding? = null
    private val binding get() = _binding!!
    internal lateinit var context: Context
    private lateinit var factory: DashboardViewModelFactory
    var dashboardPostList = ArrayList<DashboardResponse>()

    /**
     * Initial oncreate view
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FrgDashboardBinding.inflate(inflater, container, false)
        val view = binding.root
        initViewModel()
        observeDashboardData()
        setupUI()
        if (requireContext().isNetworkStatusAvailable()) {
            getDashboardPost()
        }
        return view
    }

    /**
     * Initializing viewModel and factory
     */
    private fun initViewModel() {
        factory = DashboardViewModelFactory(requireContext())
        dashboardViewModel = ViewModelProvider(this, factory).get(DashboardViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }

    /**
     * Setting up adapter and handling click event of it
     */
    private fun setupUI() {
        val layoutManager = LinearLayoutManager(activity)
        binding.actDashboardRvMain.layoutManager = layoutManager
        binding.actDashboardRvMain.itemAnimator = DefaultItemAnimator()
        binding.actDashboardRvMain.adapter = DashboardAdapter(context, dashboardPostList, object :
            DashboardItemClickListener {
            override fun dashboardItemClicked(position: Int, actionType: String) {
                (context as AppMainActivity).pushFragment(
//                    DashboardDetailFragment.newInstance(dashboardList.get(position).itemId!!),
                    DashboardDetailFragment.newInstance(),
                    true
                )
            }
        })
    }

/**
 * Post list api call
 */
    private fun getDashboardPost() {
        dashboardViewModel.getDashboardPostData()
    }

    /**
     * Observing data of post List
     */
    private fun observeDashboardData() {
        dashboardViewModel.dashboardPostData.observeOnce(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {
                dashboardPostList.addAll(listOf(it))
                binding.actDashboardRvMain.adapter?.notifyDataSetChanged()
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}


