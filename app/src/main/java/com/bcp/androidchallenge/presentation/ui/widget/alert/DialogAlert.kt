package com.bcp.androidchallenge.presentation.ui.widget.alert

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.bcp.androidchallenge.R

class DialogAlert : DialogFragment() {

    var dialogAlertListener : DialogAlertListener? = null
        set(value){
            if(value !is DialogAlertListener)
                throw RuntimeException("Class or Object must have implemented DialogAlertListener")
            else
                field = value
        }
    var action = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_alert, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.txt_title).text = arguments!!.getString("title").toString()
        view.findViewById<TextView>(R.id.txt_message).text = arguments!!.getString("value").toString()
        view.findViewById<ImageView>(R.id.img_alert).setImageResource(arguments!!.getInt("img_alert"))

        val btAccept = view.findViewById<Button>(R.id.btn_accept)
        btAccept.text = arguments!!.getString("text_button").toString()
        btAccept.setOnClickListener {

            dismiss()

            dialogAlertListener?.let {
                it.onClickListenerButtonAccept(action)
            }
        }

    }

    override fun onStart() {
        super.onStart()
        val window = dialog!!.window
        window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }


    fun setFragment(fragment: Fragment)
    {
        if(fragment !is DialogAlertListener)
            throw RuntimeException("Fragment must have implemented DialogAlertListener")
        else
            dialogAlertListener = fragment
    }

    companion object {
        fun newInstance(img_alert: Int,title: String, message: String,text_button: String): DialogAlert {
            val frag = DialogAlert()
            val args = Bundle()
            args.putInt("img_alert", img_alert)
            args.putString("title", title)
            args.putString("value", message)
            args.putString("text_button", text_button)
            frag.arguments = args
            return frag
        }
    }

    interface DialogAlertListener {
        fun onClickListenerButtonAccept(action:String)
    }

}