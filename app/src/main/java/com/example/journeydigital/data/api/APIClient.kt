package com.example.journeydigital.data.api

import com.example.journeydigital.constants.Const.CONNECTION_TIMEOUT
import com.example.journeydigital.constants.Const.baseUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.conscrypt.Conscrypt
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.Security
import java.util.concurrent.TimeUnit


open class APIClient {
    private lateinit var retrofit: Retrofit
    private var interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()

    init {
        interceptor.level = HttpLoggingInterceptor.Level.BODY
    }

    fun getClient(): Retrofit {
        val httpClient = OkHttpClient.Builder()
        Security.insertProviderAt(Conscrypt.newProvider(), 1);
        httpClient.addInterceptor(interceptor)
        httpClient.readTimeout(CONNECTION_TIMEOUT, TimeUnit.MINUTES)
        httpClient.writeTimeout(CONNECTION_TIMEOUT, TimeUnit.MINUTES)
        httpClient.connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MINUTES)
        retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl).client(httpClient.build()).build()
        Security.removeProvider("Conscrypt")
        return retrofit
    }

}