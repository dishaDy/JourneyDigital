package com.radian.myradianvaluations.networking

import android.content.Context
import android.text.TextUtils
import com.example.journeydigital.R
import com.example.journeydigital.constants.Const.baseUrl
import com.example.journeydigital.data.api.APIClient
import com.example.journeydigital.data.api.ApiResponseCallBack
import com.example.journeydigital.data.api.ReturnType
import com.example.journeydigital.utills.LogUtils
import com.google.gson.JsonElement
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.http.*


class ApiServiceProviderGeneric() : APIClient() {
    private lateinit var call: Response<JsonElement>
    private val classTag = javaClass.simpleName
    private val job = SupervisorJob()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + job)
    private lateinit var apiResponseCallBack: ApiResponseCallBack

    constructor(apiResponseCallBack: ApiResponseCallBack) : this() {
        this.apiResponseCallBack = apiResponseCallBack
    }

    fun getCall(
            context: Context,
            urlEndPoint: String,
            returnType: ReturnType,
            postId: String
    ) {
        coroutineScope.launch {
            try {
                launch(Dispatchers.Main) {
                    apiResponseCallBack.onPreExecute(returnType)
                }
                if(TextUtils.isEmpty(postId)){
                     call = getClient().create(GetCallReference::class.java)
                        .getCall(baseUrl+ urlEndPoint)
                }else{
                    call = getClient().create(GetCallReference::class.java)
                        .getCall("$baseUrl$urlEndPoint?postId=$postId")
                }

                launch(Dispatchers.Main) {
                    if (call.body() != null && call.isSuccessful) {
                        apiResponseCallBack.onSuccess(
                                returnType, call.body().toString()
                        )
                    } else {
                        apiResponseCallBack.onError(
                                returnType,
                                context.getString(R.string.please_try_again)
                        )
                    }
                }
            } catch (e: Exception) {
                launch(Dispatchers.Main) {
                    apiResponseCallBack.onError(
                            returnType,
                            context.getString(R.string.please_try_again)
                    )
                }
                LogUtils.logE(classTag, e)
            }
        }
    }

    internal interface GetCallReference {
        @GET
        suspend fun getCall(
                @Url url: String
        ): Response<JsonElement>
    }
}
