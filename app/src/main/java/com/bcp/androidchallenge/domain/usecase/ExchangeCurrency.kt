package com.bcp.androidchallenge.domain.usecase

import com.bcp.androidchallenge.data.local.database.entity.Currency
import com.bcp.androidchallenge.domain.repository.ICurrencyRepository
import com.bcp.androidchallenge.domain.util.ResultType

class ExchangeCurrency(private val currencyRepository: ICurrencyRepository): UseCase<Unit, ExchangeCurrency.Params>() {

    override suspend fun run(params :Params): ResultType<Unit>  {
        val result = currencyRepository.exchangeCurrency(params.currencySignSource, params.currencySignDestination)

        when(result){
            is ResultType.Success -> {

            }
            is ResultType.Error -> {

            }
        }

        return result
    }

    data class Params(val currencySignSource :String?, val currencySignDestination :String?)
}
