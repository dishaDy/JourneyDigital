package com.example.journeydigital.data.api


interface ApiResponseCallBack {
    fun onPreExecute(returnType: ReturnType)
    fun onSuccess(returnType: ReturnType, response: String)
    fun onError(returnType: ReturnType, error: String)
}