package com.bcp.androidchallenge.data.remote.api

import com.bcp.androidchallenge.data.remote.response.CurrencyResponse
import retrofit2.Response
import retrofit2.http.Headers
import retrofit2.http.POST

interface ICurrencyService {

    @Headers("mock:true")
    @POST("currency/list")
    suspend fun getCurrencies(): Response<CurrencyResponse>
}