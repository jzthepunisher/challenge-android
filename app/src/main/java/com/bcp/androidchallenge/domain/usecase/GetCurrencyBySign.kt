package com.bcp.androidchallenge.domain.usecase

import com.bcp.androidchallenge.data.local.database.entity.Currency
import com.bcp.androidchallenge.domain.model.CurrencyModel
import com.bcp.androidchallenge.domain.repository.ICurrencyRepository
import com.bcp.androidchallenge.domain.util.ResultType

class GetCurrencyBySign(private val currencyRepository: ICurrencyRepository): UseCase<Currency, GetCurrencyBySign.Params>()  {

    override suspend fun run(params: GetCurrencyBySign.Params): ResultType<Currency> {
        val result = currencyRepository.getCurrencyBySignBySourceOrDestination(params.exchangeRate)

        when(result){
            is ResultType.Success -> {

            }
            is ResultType.Error -> {

            }
        }

        return result
    }

    data class Params(val sign :String, val exchangeRate :String)
}