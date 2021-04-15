package com.bcp.androidchallenge.data.remote.mapper

import com.bcp.androidchallenge.data.remote.response.CurrencyResponse
import com.bcp.androidchallenge.domain.model.CurrencyModel
import kotlin.collections.ArrayList

object CurrencyMapper {
    fun toModel(currencyResponse: CurrencyResponse) :List<CurrencyModel>{

        val list = ArrayList<CurrencyModel>()
        var item: CurrencyModel
        for(currency in currencyResponse.data.currencies){

            item = CurrencyModel(currency.description, currency.euroEquivalence, currency.sign,
                currency.signDescription)
            list.add(item)
        }

         return list
    }

}