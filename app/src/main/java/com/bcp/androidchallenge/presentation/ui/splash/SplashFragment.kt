package com.bcp.androidchallenge.presentation.ui.splash

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bcp.androidchallenge.databinding.FragmentSplashBinding
import com.bcp.androidchallenge.presentation.ui.exchangerate.operation.ExchangeRateOperationFragment
import com.bcp.androidchallenge.presentation.ui.navigation.Navigation
import com.bcp.androidchallenge.presentation.util.BaseFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SplashFragment : BaseFragment() {
    private lateinit var binding: FragmentSplashBinding
    var counter = 0
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
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        showProgress()

        return binding.root

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListener()
        startTimeCounter()
    }

    private fun setupListener() {
        binding.imageViewLogo.setOnClickListener {
            tvClickImageView()
        }
    }

    private fun tvClickImageView(){
        val exchangeRateOperationFragment = ExchangeRateOperationFragment()

        Navigation.navigateTo(
            activity!!,
            exchangeRateOperationFragment,
            true
        )
    }

    fun startTimeCounter() {

        object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                counter++
            }
            override fun onFinish() {
                hideProgress()
            }
        }.start()
    }

    override fun onClickListenerButtonAccept(action :String) {}

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SplashFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SplashFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}