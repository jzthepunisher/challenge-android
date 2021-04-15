package com.bcp.androidchallenge.domain.repository

import com.bcp.androidchallenge.data.local.database.entity.Currency
import com.bcp.androidchallenge.domain.model.CurrencyModel
import com.bcp.androidchallenge.domain.util.ResultType

interface ICurrencyRepository {
    suspend fun getCurrencies(): ResultType<List<CurrencyModel>>

    suspend fun getCurrencyBySignBySourceOrDestination(exchangeRate :String): ResultType<Currency>

    suspend fun exchangeCurrency(currencySignSource :String?, currencySignDestination :String?): ResultType<Unit>

    suspend fun updateExchangeCurrency(sign :String, exchangeRate :String): ResultType<Unit>

}