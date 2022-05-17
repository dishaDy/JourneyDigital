package com.example.journeydigital.ui.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
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
import kotlinx.coroutines.*


class DashboardFragment : Fragment() {
    private lateinit var mDb: RoomBase
    private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FrgDashboardBinding? = null
    private val binding get() = _binding!!
    internal lateinit var context: Context
    private lateinit var factory: DashboardViewModelFactory
    var dashboardPostList = ArrayList<DashboardResponse>()
    private lateinit var mdashboardAdapter: DashboardAdapter
    private val job = SupervisorJob()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + job)

    /**
     * Initial on create view
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FrgDashboardBinding.inflate(inflater, container, false)
        val view = binding.root
        mDb = RoomBase.getDatabase(context)
        setHasOptionsMenu(true)
        initViewModel()
        observeDashboardData()

        if (requireContext().isNetworkStatusAvailable()) {
            getDashboardPost()
            setupUI()
        } else {
            coroutineScope.launch(Dispatchers.IO) {
                //do some background work
                dashboardPostList.clear()
                dashboardPostList.addAll(mDb.postDao().getAllPost() as ArrayList<DashboardResponse>)
                withContext(Dispatchers.Main) {
                    //Update your UI here
                    setupUI()
                }
            }
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

        if (dashboardPostList.size > 0) {
            binding.frgDashboardRvMain.makeVisible()
            binding.frgDashboardTvEmpty.makeGone()
            val layoutManager = LinearLayoutManager(activity)
            binding.frgDashboardRvMain.layoutManager = layoutManager
            binding.frgDashboardRvMain.itemAnimator = DefaultItemAnimator()

            mdashboardAdapter =
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
            binding.frgDashboardRvMain.adapter = mdashboardAdapter
        } else {
            binding.frgDashboardRvMain.makeGone()
            binding.frgDashboardTvEmpty.makeVisible()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dashboard_menu, menu)
        val search = menu.findItem(R.id.menu_search)
        var searchView   = search.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (::mdashboardAdapter.isInitialized)
                mdashboardAdapter.filter.filter(query)
                return false
            }
            override fun onQueryTextChange(query: String?): Boolean {
                if (::mdashboardAdapter.isInitialized)
                mdashboardAdapter.filter.filter(query)
                return false
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                (context as AppMainActivity).onBackPressed()
                return true
            }
        }
        return false
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
                if (it != null) {
                    coroutineScope.launch(Dispatchers.IO) {
                        mDb.postDao().deleteAll()
                        mDb.postDao().insertPost(it)
                        dashboardPostList.addAll((it))

                        withContext(Dispatchers.Main) {
                            //Update your UI here
                            setupUI()
                        }
                    }

                }

            })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}


