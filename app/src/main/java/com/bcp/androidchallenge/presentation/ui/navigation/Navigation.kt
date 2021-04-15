package com.bcp.androidchallenge.presentation.ui.navigation

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.bcp.androidchallenge.integration.ChallengeBCP

class Navigation {
    companion object {
        fun navigateTo(context: Context, fragment: Fragment, addBackStack: Boolean = true, animate: Boolean = true) {
            if(ChallengeBCP.callBackListener == null){
                if(ChallengeBCP.containerId == null)
                    throw RuntimeException("There isn't a containerId")

                val transaction = (context as FragmentActivity).supportFragmentManager.beginTransaction()

                transaction.replace(ChallengeBCP.containerId!!, fragment)

                if(addBackStack)
                    transaction.addToBackStack(null)
                transaction.commit()
            }else{
                ChallengeBCP.callBackListener?.transaction(fragment)
            }
        }
     }

}