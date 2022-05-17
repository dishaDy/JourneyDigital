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
import com.example.journeydigital.R
import com.example.journeydigital.data.Database.base.RoomBase
import com.example.journeydigital.data.model.DashboardResponse
import com.example.journeydigital.databinding.FrgDashboardBinding
import com.example.journeydigital.extensions.*
import com.example.journeydigital.ui.ViewModelFactory.DashboardViewModelFactory
import com.example.journeydigital.ui.`interface`.DashboardItemClickListener
import com.example.journeydigital.ui.adapter.DashboardAdapter
import com.example.journeydigital.ui.view.activity.AppMainActivity
import com.example.journeydigital.ui.viewmodel.DashboardViewModel

class DashboardFragment : Fragment() {
    private lateinit var db: RoomBase
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
        db=RoomBase.getDatabase(context)
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
        (context as AppMainActivity).setToolbarTitle(getString(R.string.app_name))
//        (context as AppMainActivity).binding.layoutToolbar.layToolbarTvTitle.text =
//            getString(R.string.app_name)
        if (dashboardPostList.size > 0) {
            binding.frgDashboardRvMain.makeVisible()
            binding.frgDashboardTvEmpty.makeGone()
            val layoutManager = LinearLayoutManager(activity)
            binding.frgDashboardRvMain.layoutManager = layoutManager
            binding.frgDashboardRvMain.itemAnimator = DefaultItemAnimator()
            binding.frgDashboardRvMain.adapter =
                DashboardAdapter(context, dashboardPostList, object :
                    DashboardItemClickListener {
                    override fun dashboardItemClicked(position: Int, actionType: String) {
                        (context as AppMainActivity).pushFragment(
                            DashboardDetailFragment.newInstance(
                                dashboardPostList[position].title!!,
                                dashboardPostList[position].id
                            ),
                            true
                        )
                    }
                })
        } else {
            binding.frgDashboardRvMain.makeGone()
            binding.frgDashboardTvEmpty.makeVisible()
        }

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
//                if(it!=null)
//                {
//                    db.postDao().insertPost(it)
//                }
                binding.frgDashboardRvMain.adapter?.notifyDataSetChanged()
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}


