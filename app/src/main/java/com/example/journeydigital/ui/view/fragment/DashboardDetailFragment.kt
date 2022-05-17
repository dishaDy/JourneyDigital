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
import com.example.journeydigital.constants.Const
import com.example.journeydigital.data.Database.base.RoomBase
import com.example.journeydigital.data.model.CommentResponse
import com.example.journeydigital.databinding.FrgDashboardDetailBinding
import com.example.journeydigital.extensions.*
import com.example.journeydigital.ui.ViewModelFactory.DashboardDetailViewModelFactory
import com.example.journeydigital.ui.adapter.CommentsAdapter
import com.example.journeydigital.ui.view.activity.AppMainActivity
import com.example.journeydigital.ui.viewmodel.DashboardDetailViewModel
import com.example.journeydigital.utills.LogUtils
import kotlinx.coroutines.*

class DashboardDetailFragment : Fragment() {

    private lateinit var mDb: RoomBase
    private var _binding: FrgDashboardDetailBinding? = null
    private var title: String? = null
    private var postId: Int = 0
    private lateinit var dashboardDetailViewModel: DashboardDetailViewModel
    private lateinit var factory: DashboardDetailViewModelFactory
    internal lateinit var view: View
    private val binding get() = _binding!!
    internal lateinit var context: Context
    var commentList = ArrayList<CommentResponse>()
    private lateinit var mCommentsAdapter: CommentsAdapter
    private val job = SupervisorJob()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + job)

    /**
     * Initial onCreateView
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FrgDashboardDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        mDb = RoomBase.getDatabase(context)
        setHasOptionsMenu(true)
        initViewModel()
        observeCommentData()

        arguments?.let {
            postId = it.getInt(Const.postIdTag)
            title = it.getString(Const.title)
        }
        initViewModel()
        observeCommentData()
        setUpUI()
        if (requireContext().isNetworkStatusAvailable()) {
            getComments()
            setUpUI()
        } else {
            coroutineScope.launch(Dispatchers.IO) {
                //do some background work
                commentList.clear()
                commentList.addAll(
                    mDb.commentDao().getAllCommentById(postId) as ArrayList<CommentResponse>
                )
                withContext(Dispatchers.Main) {
                    //Update your UI here
                    setUpUI()
                }
            }
        }
        return view
    }

    /**
     * Setiing up UI of detail screen
     */
    private fun setUpUI() {
        (context as AppMainActivity).setToolbarTitle(title.toString())

        if (commentList.size > 0) {
            binding.frgDashboardDetailRvComment.makeVisible()
            binding.frgDashboardDetailTvEmpty.makeGone()
            val layoutManager = LinearLayoutManager(activity)
            binding.frgDashboardDetailRvComment.layoutManager = layoutManager
            binding.frgDashboardDetailRvComment.itemAnimator = DefaultItemAnimator()
            mCommentsAdapter = CommentsAdapter(context, commentList)
            binding.frgDashboardDetailRvComment.adapter = mCommentsAdapter

        } else {
            binding.frgDashboardDetailRvComment.makeGone()
            binding.frgDashboardDetailTvEmpty.makeVisible()
        }
    }


    /**
     * Added menuitems to implement search
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dashboard_menu, menu)
        val search = menu.findItem(R.id.menu_search)
        var searchView = search.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (::mCommentsAdapter.isInitialized)
                    mCommentsAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (::mCommentsAdapter.isInitialized)
                    mCommentsAdapter.filter.filter(query)
                return false
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }

    /**
     * Created instance for data passing
     */
    companion object {
        fun newInstance(title: String, postId: Int): DashboardDetailFragment {
            val args = Bundle()
            args.putInt(Const.postIdTag, postId)
            args.putString(Const.title, title)
            val fragment = DashboardDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    /**
     * Initializing viewModel and factory
     */
    private fun initViewModel() {
        factory = DashboardDetailViewModelFactory(requireContext())
        dashboardDetailViewModel =
            ViewModelProvider(this, factory).get(DashboardDetailViewModel::class.java)
    }

    /**
     * comment list api call
     */
    private fun getComments() {
        dashboardDetailViewModel.getCommentData(postId)
    }

    /**
     * Observing data of post List
     */
    private fun observeCommentData() {
        dashboardDetailViewModel.commentData.observeOnce(
            viewLifecycleOwner,
            androidx.lifecycle.Observer {

                coroutineScope.launch(Dispatchers.IO) {
                    if (it != null) {
                        mDb.commentDao().deleteAllCommentById(postId)
                        mDb.commentDao().insertComment(it)
                        commentList.addAll(it)
                    }
                    withContext(Dispatchers.Main) {
                        setUpUI()
                    }
                }

            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}