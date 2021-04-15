package com.bcp.androidchallenge.data.remote.api

import android.content.Context
import com.bcp.androidchallenge.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Api {

    private var retrofit: Retrofit? = null
    private var _baseUrl :String = "http://192.168.137.1:9081/currency/api/"
    private var _context :Context? = null
    init {
        //buildRetrofit("http://192.168.137.1:9081/currency/api/")
    }

    fun buildRetrofit(baseUrl: String, context :Context?){
        _baseUrl = baseUrl
        _context = context

        retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(_baseUrl)
            .client(getOkHttp())
            .build()
    }

    fun create(): ICurrencyService {
        return retrofit!!.create(ICurrencyService::class.java)
    }

    private fun getOkHttp(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()

        if(BuildConfig.DEBUG)
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        else
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE

        return OkHttpClient.Builder()
            .connectTimeout(70, TimeUnit.SECONDS)
            .writeTimeout(70, TimeUnit.SECONDS)
            .readTimeout(70, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            //.addNetworkInterceptor(CurrencyResponseInterceptor(_context as Context))
            .addInterceptor(CurrencyResponseInterceptor(_context as Context))
            .build()
    }

}