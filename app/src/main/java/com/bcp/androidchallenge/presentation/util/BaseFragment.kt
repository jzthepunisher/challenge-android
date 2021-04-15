package com.bcp.androidchallenge.presentation.util

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bcp.androidchallenge.R
import com.bcp.androidchallenge.integration.ChallengeActivityListener
import com.bcp.androidchallenge.presentation.ui.widget.alert.DialogAlert
import java.lang.RuntimeException

abstract class BaseFragment : Fragment(), DialogAlert.DialogAlertListener  {

    fun showProgress() {
        (activity as ChallengeActivityListener).showProgress()
    }

    fun hideProgress() {
        (activity as ChallengeActivityListener).hideProgress()
    }

    @Suppress("UNCHECKED_CAST")
    protected inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(aClass: Class<T>):T = f() as T
    }

    fun showErrorGeneric(){
        val dialog = DialogAlert.newInstance(
            R.drawable.ic_dialog_problem,
            getString(R.string.dialog_error_generic_title),
            getString(R.string.dialog_error_generic_message),
            getString(R.string.dialog_error_generic_button_title))
        dialog.show(fragmentManager!!, "DialogAlert")
    }

    fun showErrorConnection(action :String){
        val dialog = DialogAlert.newInstance(
            R.drawable.ic_dialog_connection,
            getString(R.string.dialog_error_connection_title),
            getString(R.string.dialog_error_connection_message),
            getString(R.string.dialog_error_connection_button_title))

        dialog.dialogAlertListener = this
        dialog.action = action

        dialog.show(fragmentManager!!, "DialogAlert")
    }


}