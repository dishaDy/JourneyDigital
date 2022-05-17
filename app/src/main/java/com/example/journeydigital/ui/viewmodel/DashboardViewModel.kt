package com.example.journeydigital.ui.viewmodel

import android.content.Context
import android.content.DialogInterface
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.journeydigital.R
import com.example.journeydigital.data.api.ApiResponseCallBack
import com.example.journeydigital.data.api.ReturnType
import com.example.journeydigital.data.model.DashboardResponse
import com.example.journeydigital.utills.LoadingDialog
import com.example.journeydigital.utills.LogUtils
import com.example.journeydigital.utills.Utility
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.radian.myradianvaluations.networking.ApiServiceProviderGeneric
import kotlinx.coroutines.launch
import java.lang.reflect.Type

class DashboardViewModel(private val context: Context) : ViewModel(), ApiResponseCallBack {
    private var dashboardPostResponse = MutableLiveData<MutableList<DashboardResponse>>()

    private val apiServiceProviderGeneric = ApiServiceProviderGeneric(this)

    val dashboardPostData: MutableLiveData<MutableList<DashboardResponse>>
        get() = dashboardPostResponse

    fun getDashboardPostData() {
        apiServiceProviderGeneric.getCall(
            context,
            ReturnType.GET_DashboardPost.endPoint,
            ReturnType.GET_DashboardPost,""
        )
    }

    override fun onPreExecute(returnType: ReturnType) {
        LoadingDialog.show(context)
    }

    override fun onSuccess(returnType: ReturnType, response: String) {
        LoadingDialog.dismissDialog()
        try {
            when (returnType) {
                ReturnType.GET_DashboardPost -> {
                    val response = Gson().fromJson<List<DashboardResponse>>(
                        response,
                        object : TypeToken<List<DashboardResponse>>() {}.type
                    )
//                    LogUtils.logD("", "" + response.status)
                    dashboardPostResponse.value = response as MutableList<DashboardResponse>?
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