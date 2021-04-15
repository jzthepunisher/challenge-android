package com.bcp.androidchallenge.presentation.ui.bindingadapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bcp.androidchallenge.presentation.util.Data
import java.text.DecimalFormat

@BindingAdapter("imageResource")
fun setImageResource(view: ImageView, sign: String) {
    if (sign !== null) {
        Data.flags.get(sign)?.let {
            view.setImageResource(it)
        }
    }
}

@BindingAdapter("formatAmount")
fun setFormatAmount(textView: TextView, amount: Double) {
    val decimalFormat = DecimalFormat("#,##0.00")
    textView.text = "${decimalFormat.format(amount)}"
}