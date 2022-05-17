package com.example.journeydigital.ui.viewmodel

import android.content.Context
import android.content.DialogInterface
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.journeydigital.R
import com.example.journeydigital.data.api.ApiResponseCallBack
import com.example.journeydigital.data.api.ReturnType
import com.example.journeydigital.data.model.CommentResponse
import com.example.journeydigital.utills.LoadingDialog
import com.example.journeydigital.utills.LogUtils
import com.example.journeydigital.utills.Utility
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.radian.myradianvaluations.networking.ApiServiceProviderGeneric

class DashboardDetailViewModel (private val context: Context) : ViewModel(), ApiResponseCallBack {
    private var commentResponse = MutableLiveData<MutableList<CommentResponse>>()

    private val apiServiceProviderGeneric = ApiServiceProviderGeneric(this)

    val commentData: MutableLiveData<MutableList<CommentResponse>>
        get() = commentResponse

    fun getCommentData(postId: Int) {
        apiServiceProviderGeneric.getCall(
            context,
            ReturnType.GET_Comment.endPoint,
            ReturnType.GET_Comment,postId.toString()
        )
    }

    override fun onPreExecute(returnType: ReturnType) {
        LoadingDialog.show(context)
    }

    override fun onSuccess(returnType: ReturnType, response: String) {
        LoadingDialog.dismissDialog()
        try {
            when (returnType) {
                ReturnType.GET_Comment -> {
                    val response = Gson().fromJson<List<CommentResponse>>(
                        response,
                        object : TypeToken<List<CommentResponse>>() {}.type
                    )
//                    LogUtils.logD("", "" + response.status)
                    commentResponse.value = response as MutableList<CommentResponse>?
                }
            }

        } catch (e: Exception) {
            LogUtils.logE("", e)
        }
    }

    override fun onError(returnType: ReturnType, error: String) {
        LoadingDialog.dismissDialog()
        Utility.showOkDialog(
            context!!,
            context.getString(R.string.please_try_again),
            DialogInterface.OnClickListener { _, _ -> },
            context.getString(R.string.ok)
        )
    }
}