package com.example.journeydigital.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.journeydigital.R

class DashboardDetailFragment: Fragment() {

    internal lateinit var view: View


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.frg_dashboard_detail, container, false)

        return view
    }
    companion object {
//        fun newInstance(itemId: Int): DashboardDetailFragment {
        fun newInstance(): DashboardDetailFragment {
            val args = Bundle()
//            args.putInt(Const.itemIdTag, itemId)
            val fragment = DashboardDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }
}