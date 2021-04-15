package com.bcp.androidchallenge.presentation.ui.exchangerate.list

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bcp.androidchallenge.application.ChallengeApplication
import com.bcp.androidchallenge.data.repository.CurrencyDataRepository
import com.bcp.androidchallenge.databinding.FragmentExchangeRateListBinding
import com.bcp.androidchallenge.domain.usecase.GetCurrency
import com.bcp.androidchallenge.domain.usecase.UpdateExchangeRate
import com.bcp.androidchallenge.presentation.ui.exchangerate.list.adapter.CurrencyAdapter
import com.bcp.androidchallenge.presentation.ui.exchangerate.list.adapter.DividerCategoryTop
import com.bcp.androidchallenge.presentation.ui.exchangerate.operation.ExchangeRateOperationFragment
import com.bcp.androidchallenge.presentation.ui.navigation.Navigation
import com.bcp.androidchallenge.presentation.ui.util.EventObserver
import com.bcp.androidchallenge.presentation.util.BaseFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ExchangeRateListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExchangeRateListFragment : BaseFragment() {
    private lateinit var binding: FragmentExchangeRateListBinding
    private lateinit var viewModel: ExchangeRateListViewModel
    private var _exchangeRate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*arguments?.let {
            _exchangeRate = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExchangeRateListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCurrencyAdapter()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setArguments()
        viewModel = ViewModelProviders.of(this, viewModelFactory { createViewModel() })
            .get(ExchangeRateListViewModel::class.java)
        binding.viewModel = viewModel

        ///shareViewModel = ViewModelProviders.of(activity!!).get(UpdateViewModel::class.java)
        observerViewModel()
    }

    private fun observerViewModel() {
        viewModel.listCurrency.observe(viewLifecycleOwner, Observer {

            if (it.count() > 0) {
                (binding.recyclerViewCurrency.adapter as CurrencyAdapter).items = it
                binding.recyclerViewCurrency.visibility = View.VISIBLE

            } else {
                binding.recyclerViewCurrency.visibility = View.GONE
            }

        })

        viewModel.showMessage.observe(this, Observer {
            when (it) {
                is ExchangeRateListViewModel.Message.ErrorGetCurrencies -> showErrorGeneric()
                is ExchangeRateListViewModel.Message.ErrorConnection -> showErrorConnection("")
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

        viewModel.updateExchangeRateEvent.observe(viewLifecycleOwner, EventObserver {
            val exchangeRateOperationFragment = ExchangeRateOperationFragment()
            Navigation.navigateTo(activity as Context, exchangeRateOperationFragment)
            //fragmentManager?.popBackStack()
        })
    }

    private fun setupCurrencyAdapter() {
        val linearLayoutManager = LinearLayoutManager(context)

        val  currencyAdapter= CurrencyAdapter(activity as Context)
        currencyAdapter.onItemClicked = { _, currencyModel ->
            viewModel.updateExchangeRate(currencyModel.sign, _exchangeRate)
        }

        binding.recyclerViewCurrency.apply {
            addItemDecoration(DividerCategoryTop(context))
            layoutManager = linearLayoutManager
            adapter = currencyAdapter
        }

    }

    private fun createViewModel(): ExchangeRateListViewModel {
        val repository = CurrencyDataRepository((activity?.application as ChallengeApplication).database.currencyDao())
        val useCase = GetCurrency(repository)
        val updateExchangeRateUseCase = UpdateExchangeRate(repository)

        return ExchangeRateListViewModel(useCase, updateExchangeRateUseCase)
    }

    private fun setArguments(){
        _exchangeRate = arguments?.getString(ARGUMENT_EXCHANGE_RATE) ?: ""
    }

    override fun onClickListenerButtonAccept(action :String) {}

    companion object {
        const val ARGUMENT_EXCHANGE_RATE = "exchange_rate"
        @JvmStatic
        fun newInstance(exchangeRate: String) =
            ExchangeRateListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARGUMENT_EXCHANGE_RATE, exchangeRate)
                }
            }
    }
}