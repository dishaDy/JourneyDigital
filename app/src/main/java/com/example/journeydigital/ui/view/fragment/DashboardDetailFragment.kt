package com.example.journeydigital.ui.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.journeydigital.constants.Const
import com.example.journeydigital.data.model.CommentResponse
import com.example.journeydigital.databinding.FrgDashboardDetailBinding
import com.example.journeydigital.extensions.*
import com.example.journeydigital.ui.ViewModelFactory.DashboardDetailViewModelFactory
import com.example.journeydigital.ui.adapter.CommentsAdapter
import com.example.journeydigital.ui.view.activity.AppMainActivity
import com.example.journeydigital.ui.viewmodel.DashboardDetailViewModel

class DashboardDetailFragment : Fragment() {

    private var _binding: FrgDashboardDetailBinding? = null
    private var title: String? = null
    private var postId: Int = 0
    private lateinit var dashboardDetailViewModel: DashboardDetailViewModel
    private lateinit var factory: DashboardDetailViewModelFactory
    internal lateinit var view: View
    private val binding get() = _binding!!
    internal lateinit var context: Context
    var commentList = ArrayList<CommentResponse>()

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
        arguments?.let {
            postId = it.getInt(Const.postIdTag)
            title = it.getString(Const.title)
        }
        initViewModel()
        observeCommentData()
        setUpUI()
        getComments()
        return view
    }

    /**
     * Setiing up UI of detail screen
     */
    private fun setUpUI() {
        (context as AppMainActivity).setToolbarTitle(title.toString())
        (context as AppMainActivity).setBackVisible(true)

        if (commentList.size > 0) {
            binding.frgDashboardDetailRvComment.makeVisible()
            binding.frgDashboardDetailTvEmpty.makeGone()
            val layoutManager = LinearLayoutManager(activity)
            binding.frgDashboardDetailRvComment.layoutManager = layoutManager
            binding.frgDashboardDetailRvComment.itemAnimator = DefaultItemAnimator()
            binding.frgDashboardDetailRvComment.adapter = CommentsAdapter(context, commentList)
        } else {
            binding.frgDashboardDetailRvComment.makeGone()
            binding.frgDashboardDetailTvEmpty.makeVisible()
        }
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
                commentList.addAll(it)
                setUpUI()
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
}