package com.bcp.androidchallenge.data.repository

import androidx.annotation.StringRes
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.bcp.androidchallenge.data.local.database.dao.CurrencyDao
import com.bcp.androidchallenge.data.local.database.entity.Currency
import com.bcp.androidchallenge.data.remote.api.Api
import com.bcp.androidchallenge.data.remote.api.ICurrencyService
import com.bcp.androidchallenge.data.remote.mapper.CurrencyMapper
import com.bcp.androidchallenge.domain.model.CurrencyModel
import com.bcp.androidchallenge.domain.repository.ICurrencyRepository
import com.bcp.androidchallenge.domain.usecase.GetCurrency
import com.bcp.androidchallenge.domain.util.*
import com.bcp.androidchallenge.presentation.ui.util.Event
import kotlinx.coroutines.launch
import java.lang.Exception

class CurrencyDataRepository(private val currencyDao: CurrencyDao) : ICurrencyRepository {

    private val api: ICurrencyService = Api.create()
    //////val allWords : LiveData<List<Currency>> = currencyDao.getCurrencies()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(currency :Currency) {
        currencyDao.insert(currency)
    }

    override suspend fun getCurrencies() = safeApiCall {

        val response = api.getCurrencies()

        if (response.isSuccessful) {
            val responseConverted = CurrencyMapper.toModel(response.body()!!)
            insertCurrenciesLocal(responseConverted)
            ResultType.Success(responseConverted)
        } else {
            ResultType.Error(ErrorGeneric())
        }
    }

    suspend fun insertCurrenciesLocal(responseConverted :List<CurrencyModel>){
        var item: Currency
        for(currencyModel in responseConverted){

            var exchangeRate :String = ""
            exchangeRate = if (currencyModel.sign == "USD")  "SOURCE" else  if (currencyModel.sign == "SOL")  "DESTINATION" else ""

            item = Currency(currencyModel.description, currencyModel.euroEquivalence, currencyModel.sign,
                currencyModel.signDescription,exchangeRate)
            currencyDao.insert(item)
        }
    }

    override suspend fun getCurrencyBySignBySourceOrDestination(exchangeRate :String) = safeApiCall {

        val currency = currencyDao.getCurrencySourceOrDestination( exchangeRate)

        if (currency !== null) {
            ResultType.Success(currency)
        } else {
            //ResultType.Success(null)
            ResultType.Error(ErrorNoExist())
        }

    }

    override suspend fun exchangeCurrency(currencySignSource :String?, currencySignDestination :String?) = safeApiCall {
        try {
            currencyDao.exchangeCurrencySource(currencySignSource)
            currencyDao.exchangeCurrencyDestination(currencySignDestination)
            ResultType.Success(Unit)
        }catch (e :Exception ){
            ResultType.Error(ErrorUpdate())
        }
    }

    override suspend fun updateExchangeCurrency(sign :String, exchangeRate :String) = safeApiCall {
        try {
            currencyDao.clearExchageRate(exchangeRate)
            currencyDao.updateExchageRate(sign, exchangeRate)
            ResultType.Success(Unit)
        }catch (e :Exception ){
            ResultType.Error(ErrorUpdate())
        }
    }
}