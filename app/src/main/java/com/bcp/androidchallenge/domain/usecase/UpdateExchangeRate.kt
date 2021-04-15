package com.bcp.androidchallenge.domain.usecase

import com.bcp.androidchallenge.domain.repository.ICurrencyRepository
import com.bcp.androidchallenge.domain.util.ResultType

class UpdateExchangeRate (private val currencyRepository: ICurrencyRepository): UseCase<Unit, UpdateExchangeRate.Params>() {

    override suspend fun run(params :Params): ResultType<Unit> {
        val result = currencyRepository.updateExchangeCurrency(params.sign, params.exchangeRate)

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