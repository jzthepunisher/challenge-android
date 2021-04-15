package com.bcp.androidchallenge.presentation.ui.widget.progress

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import com.bcp.androidchallenge.R

class ProgressDialog(context: Context) : Dialog(context) {


    init {
        val wdd = window!!.attributes
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)


        wdd.gravity = Gravity.CENTER_HORIZONTAL
        window!!.attributes = wdd
        setTitle(null)
        setCancelable(false)
        setOnCancelListener(null)
        val view = LayoutInflater.from(context).inflate(R.layout.progress_dialog, null)
        setContentView(view)
    }


}