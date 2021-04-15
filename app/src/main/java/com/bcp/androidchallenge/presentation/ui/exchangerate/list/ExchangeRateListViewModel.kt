package com.bcp.androidchallenge.presentation.ui.exchangerate.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.bcp.androidchallenge.domain.model.CurrencyModel
import com.bcp.androidchallenge.domain.usecase.GetCurrency
import com.bcp.androidchallenge.domain.usecase.UpdateExchangeRate
import com.bcp.androidchallenge.domain.util.ErrorGeneric
import com.bcp.androidchallenge.domain.util.ResultType
import com.bcp.androidchallenge.presentation.util.BaseViewModel
import com.bcp.androidchallenge.presentation.ui.util.Event
import kotlinx.coroutines.launch
import java.io.IOException

class ExchangeRateListViewModel(private val getCurrency :GetCurrency,
                                private val updateExchangeRate : UpdateExchangeRate
): BaseViewModel()  {

    private val _progress = MutableLiveData<Boolean>()
    val progress: LiveData<Boolean> = _progress

    private val _listCurrency = MutableLiveData<Event<List<CurrencyModel>>>()
    val listCurrency :LiveData<List<CurrencyModel>> = Transformations.map(_listCurrency){ data ->
        data.getContentIfNotHandled()
    }

    private val _updateExchangeRateEvent = MutableLiveData<Event<Unit>>()
    val updateExchangeRateEvent: LiveData<Event<Unit>> = _updateExchangeRateEvent

    private val _showMessage = MutableLiveData<Message>()
    val showMessage: LiveData<Message> = _showMessage

    init {
        getCurrencies()
    }

    fun getCurrencies(){

        viewModelScope.launch {
            _progress.value = true
            when(val result = getCurrency.run(GetCurrency.Params(""))){
                is ResultType.Success -> {
                    _listCurrency.value = Event(result.data)
                }
                is ResultType.Error -> {handlerError(result.exception)}
            }

            _progress.value = false
        }

    }

    fun updateExchangeRate(sign :String, exchange_rate :String){
        viewModelScope.launch {
            _progress.value = true
            when(val result = updateExchangeRate.run(UpdateExchangeRate.Params(sign, exchange_rate))){
                is ResultType.Success -> {
                    _updateExchangeRateEvent.value = Event(result.data)
                }
                is ResultType.Error -> {handlerError(result.exception)}
            }
            _progress.value = false
        }
    }

    private fun handlerError(exception: Exception) {
        when(exception){
            is ErrorGeneric -> {
                _showMessage.value = Message.ErrorGetCurrencies
            }
            is IOException ->{
                if(exception.message.equals("false")){
                    _showMessage.value = Message.ErrorConnection
                }
            }

        }
    }

    sealed class Message{
        object ErrorGetCurrencies: Message()
        object ErrorConnection: Message()
    }
}