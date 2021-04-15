package com.bcp.androidchallenge.integration

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.bcp.androidchallenge.presentation.ui.exchangerate.list.ExchangeRateListFragment
import com.bcp.androidchallenge.presentation.ui.exchangerate.operation.ExchangeRateOperationFragment
import com.bcp.androidchallenge.presentation.ui.navigation.Navigation
import com.bcp.androidchallenge.presentation.ui.splash.SplashFragment

object ChallengeBCP {

    var containerId: Int? = null
        private set

    var callBackListener :FragmentTransactionHelper? = null
        private set

    fun loadMainFragment(context: Context, contentFragment: Int, fragmentTransactionHelper : FragmentTransactionHelper?){
        this.callBackListener = fragmentTransactionHelper

        containerId = contentFragment

        Navigation.navigateTo(context, SplashFragment(), addBackStack = false, animate = false)
    }

    interface FragmentTransactionHelper{
        fun transaction(fragment: Fragment)
    }
}