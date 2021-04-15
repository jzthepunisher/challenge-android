package com.bcp.androidchallenge.presentation.ui.exchangerate.operation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bcp.androidchallenge.R
import com.bcp.androidchallenge.application.ChallengeApplication
import com.bcp.androidchallenge.data.local.database.entity.Currency
import com.bcp.androidchallenge.data.repository.CurrencyDataRepository
import com.bcp.androidchallenge.databinding.FragmentExchangeRateOperationBinding
import com.bcp.androidchallenge.databinding.FragmentSplashBinding
import com.bcp.androidchallenge.domain.usecase.ExchangeCurrency
import com.bcp.androidchallenge.domain.usecase.GetCurrency
import com.bcp.androidchallenge.domain.usecase.GetCurrencyBySign
import com.bcp.androidchallenge.presentation.ui.bindingadapter.setFormatAmount
import com.bcp.androidchallenge.presentation.ui.exchangerate.list.ExchangeRateListFragment
import com.bcp.androidchallenge.presentation.ui.exchangerate.list.ExchangeRateListViewModel
import com.bcp.androidchallenge.presentation.ui.exchangerate.list.adapter.CurrencyAdapter
import com.bcp.androidchallenge.presentation.ui.navigation.Navigation
import com.bcp.androidchallenge.presentation.util.BaseFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ExchangeRateOperationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExchangeRateOperationFragment : BaseFragment() {
    private lateinit var binding: FragmentExchangeRateOperationBinding
    private lateinit var viewModel: ExchangeRateOperationViewModel
    private var currencySource :Currency? = null
    private var currencyDestination :Currency? = null
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExchangeRateOperationBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListener()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory { createViewModel() })
            .get(ExchangeRateOperationViewModel::class.java)
        binding.viewModel = viewModel

        ///shareViewModel = ViewModelProviders.of(activity!!).get(UpdateViewModel::class.java)
        observerViewModel()
    }

    private fun setupListener() {
        binding.buttonStartYourOperation.setOnClickListener {
            tvClickButtonStartYourOperation()
        }

        binding.imageViewExchange.setOnClickListener {
            tvClickImageExchangeCurrency()
        }

        binding.textViewYouSendCurrencyDescription.setOnLongClickListener  {
            onLongClickTextViewYouSendCurrencyDescription()
        }

        binding.textViewYouGetCurrencyDescription.setOnLongClickListener  {
            onLongClickTextViewYouGetCurrencyDescription()
        }

    }

    private fun observerViewModel() {
        viewModel.currencyDefaultSource.observe(viewLifecycleOwner, Observer {

            if (it !== null) {
                binding.textViewYouSendCurrencyDescription.text = it.signDescription
                currencySource = it
                getBuyAndSale(currencySource,currencyDestination)
                //binding.recyclerViewCurrency.visibility = View.VISIBLE

            } else {
                binding.textViewYouSendCurrencyDescription.text = ""
                //binding.recyclerViewCurrency.visibility = View.GONE
            }

        })

        viewModel.currencyDefaultDestination.observe(viewLifecycleOwner, Observer {

            if (it !== null) {
                binding.textViewYouGetCurrencyDescription.text = it.signDescription

                currencyDestination = it
                //binding.recyclerViewCurrency.visibility = View.VISIBLE
                getBuyAndSale(currencySource,currencyDestination)
            } else {
                binding.textViewYouGetCurrencyDescription.text = ""
                //binding.recyclerViewCurrency.visibility = View.GONE
            }

        })

        viewModel.showMessage.observe(this, Observer {
            when (it) {
                is ExchangeRateOperationViewModel.Message.ErrorGetCurrencies -> showErrorGeneric()
                is ExchangeRateOperationViewModel.Message.ErrorConnection -> showErrorConnection("")
            }
        })

        viewModel.progress.observe(this, Observer { isProgress ->
            if (isProgress) {
                showProgress()
                ///hideCard()
            } else {
                hideProgress()
                ////showCard()
            }
        })
    }

    private fun getBuyAndSale(currencySource :Currency?, currencyDestination :Currency?){

        if (currencySource !== null && currencyDestination !== null){
            var buyValue = currencySource.euroEquivalence / currencyDestination.euroEquivalence
            setFormatAmount(binding.textViewBuy, buyValue )
            setFormatAmount(binding.textViewSale, buyValue + 0.02)
            //binding.textViewBuy.text = buyValue.toString()
            //binding.textViewSale.text = (buyValue + 0.02).toString()
        }

    }

    private fun tvClickButtonStartYourOperation(){

        getGetConversion(currencySource, currencyDestination)

    }

    private fun tvClickImageExchangeCurrency(){
        viewModel.exchangeCurrency(currencySource?.sign, currencyDestination?.sign)
    }

    private fun onLongClickTextViewYouSendCurrencyDescription(): Boolean{
        val exchangeRateListFragment = ExchangeRateListFragment.newInstance("SOURCE")
        Navigation.navigateTo(
            activity!!,
            exchangeRateListFragment,
            true
        )
        return true
    }

    private fun onLongClickTextViewYouGetCurrencyDescription(): Boolean {
        val exchangeRateListFragment = ExchangeRateListFragment.newInstance("DESTINATION")
        Navigation.navigateTo(
            activity!!,
            exchangeRateListFragment,
            true
        )
        return true
    }

    private fun getGetConversion(currencySource :Currency?, currencyDestination :Currency?){

        if (currencySource !== null && currencyDestination !== null){
            val amountToConversion = binding.editTextYouSend.text.toString().toDouble()
            var conversionValue = amountToConversion * currencySource.euroEquivalence / currencyDestination.euroEquivalence

            setFormatAmount(binding.editTextYouGet, conversionValue )
        }
    }

    private fun createViewModel(): ExchangeRateOperationViewModel {
        val repository = CurrencyDataRepository((activity?.application as ChallengeApplication).database.currencyDao())
        val useCase = GetCurrencyBySign(repository)
        val exchangeCurrencyUseCase = ExchangeCurrency(repository)

        return ExchangeRateOperationViewModel(useCase,exchangeCurrencyUseCase)
    }

    override fun onClickListenerButtonAccept(action :String) {}

    companion object {
        const val ARGUMENT_CURRENCY_DESCRIPTION = "currency_description"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ExchangeRateOperationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ExchangeRateOperationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}