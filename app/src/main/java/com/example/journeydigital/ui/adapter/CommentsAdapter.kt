package com.example.journeydigital.ui.adapter

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.journeydigital.R
import com.example.journeydigital.data.model.CommentResponse
import com.example.journeydigital.databinding.RowCommentBinding
import com.example.journeydigital.extensions.makeGone
import com.example.journeydigital.extensions.makeVisible

class CommentsAdapter(
    private val context: Context, private val commentList: ArrayList<CommentResponse>
) : RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_comment, parent, false)
        return CommentsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        with(holder) {

            if (!TextUtils.isEmpty(commentList[position].name)) {
                binding.rowCommentTvName.text = commentList[position].name
                binding.rowCommentTvName.makeVisible()
            } else {
                binding.rowCommentTvName.makeGone()
            }

            if (!TextUtils.isEmpty(commentList[position].email)) {
                binding.rowCommentTvEmail.text = commentList[position].email
                binding.rowCommentTvEmail.makeVisible()
            } else {
                binding.rowCommentTvEmail.makeGone()
            }

            if (!TextUtils.isEmpty(commentList[position].body)) {
                binding.rowCommentTvComment.text = commentList[position].body
                binding.rowCommentTvComment.makeVisible()
            } else {
                binding.rowCommentTvComment.makeGone()
            }
        }
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    inner class CommentsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = RowCommentBinding.bind(view)

    }
}