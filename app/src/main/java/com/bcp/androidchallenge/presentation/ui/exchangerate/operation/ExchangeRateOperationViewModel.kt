package com.bcp.androidchallenge.presentation.ui.exchangerate.operation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.bcp.androidchallenge.data.local.database.entity.Currency
import com.bcp.androidchallenge.domain.model.CurrencyModel
import com.bcp.androidchallenge.domain.usecase.ExchangeCurrency
import com.bcp.androidchallenge.domain.usecase.GetCurrency
import com.bcp.androidchallenge.domain.usecase.GetCurrencyBySign
import com.bcp.androidchallenge.domain.util.ErrorGeneric
import com.bcp.androidchallenge.domain.util.ErrorNoExist
import com.bcp.androidchallenge.domain.util.ResultType
import com.bcp.androidchallenge.presentation.ui.exchangerate.list.ExchangeRateListViewModel
import com.bcp.androidchallenge.presentation.ui.util.Event
import com.bcp.androidchallenge.presentation.util.BaseViewModel
import kotlinx.coroutines.launch
import java.io.IOException

class ExchangeRateOperationViewModel(private val getCurrencyBySign: GetCurrencyBySign,
                                     private val exchangeCurrency: ExchangeCurrency): BaseViewModel()  {
    private val _progress = MutableLiveData<Boolean>()
    val progress: LiveData<Boolean> = _progress

    private val _currencyDefaultSource = MutableLiveData<Event<Currency>>()
    val currencyDefaultSource :LiveData<Currency> = Transformations.map(_currencyDefaultSource){ data ->
        data.getContentIfNotHandled()
    }

    private val _currencyDefaultDestination = MutableLiveData<Event<Currency>>()
    val currencyDefaultDestination :LiveData<Currency> = Transformations.map(_currencyDefaultDestination){ data ->
        data.getContentIfNotHandled()
    }

    private val _exchangeCurrencyEvent = MutableLiveData<Event<Unit>>()
    val exchangeCurrencyEvent: LiveData<Event<Unit>> = _exchangeCurrencyEvent

    private val _showMessage = MutableLiveData<ExchangeRateOperationViewModel.Message>()
    val showMessage: LiveData<ExchangeRateOperationViewModel.Message> = _showMessage

    init {
        getCurrenySource("USD","SOURCE")
        getCurrenyDestination("SOL","DESTINATION")
    }

    fun getCurrenySource(sign :String, exchangeRate :String){
        viewModelScope.launch {
            _progress.value = true
            when(val result = getCurrencyBySign.run(GetCurrencyBySign.Params(sign, exchangeRate))){
                is ResultType.Success -> {
                    _currencyDefaultSource.value = Event(result.data)
                }
                is ResultType.Error -> {handlerError(result.exception)}
            }
            _progress.value = false
        }
    }

    fun getCurrenyDestination(sign :String, exchangeRate :String){
        viewModelScope.launch {
            _progress.value = true
            when(val result = getCurrencyBySign.run(GetCurrencyBySign.Params(sign, exchangeRate))){
                is ResultType.Success -> {
                    _currencyDefaultDestination.value = Event(result.data)
                }
                is ResultType.Error -> {handlerError(result.exception)}
            }
            _progress.value = false
        }
    }

    fun exchangeCurrency(currencySignSource :String?, currencySignDestination :String?){
        if (currencySignSource !== null && currencySignDestination !== null){
            viewModelScope.launch {
                _progress.value = true
                when(val result = exchangeCurrency.run(ExchangeCurrency.Params(currencySignSource, currencySignDestination))){
                    is ResultType.Success -> {
                        _exchangeCurrencyEvent.value = Event(result.data)
                        getCurrenySource("","SOURCE")
                        getCurrenyDestination("","DESTINATION")
                    }
                    is ResultType.Error -> {handlerError(result.exception)}
                }
                _progress.value = false
            }
        }
    }

    private fun handlerError(exception: Exception) {
        when(exception){
            is ErrorGeneric -> {
                _showMessage.value = Message.ErrorGetCurrencies
            }
            is ErrorNoExist ->{
                _showMessage.value = Message.ErrorNotExist
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
        object ErrorNotExist: Message()
        object ErrorConnection: Message()
    }
}