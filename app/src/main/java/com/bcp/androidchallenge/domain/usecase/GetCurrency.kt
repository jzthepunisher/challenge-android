package com.bcp.androidchallenge.domain.usecase

import com.bcp.androidchallenge.domain.model.CurrencyModel
import com.bcp.androidchallenge.domain.repository.ICurrencyRepository
import com.bcp.androidchallenge.domain.util.ResultType

class GetCurrency (private val currencyRepository: ICurrencyRepository): UseCase<List<CurrencyModel>, GetCurrency.Params>() {

    override suspend fun run(params: Params): ResultType<List<CurrencyModel>>{
        val result = currencyRepository.getCurrencies(params.month, params.year)
        when(result){
            is ResultType.Success -> {

            }
            is ResultType.Error -> {

            }
        }

        return result
    }

    data class Params(val month: Int, val year: Int)
}