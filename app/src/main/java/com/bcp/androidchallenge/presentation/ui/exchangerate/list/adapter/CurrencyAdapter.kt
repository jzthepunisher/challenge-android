package com.bcp.androidchallenge.presentation.ui.exchangerate.list.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bcp.androidchallenge.databinding.ItemCurrencyBinding
import com.bcp.androidchallenge.domain.model.CurrencyModel
import com.bcp.androidchallenge.presentation.ui.exchangerate.operation.ExchangeRateOperationFragment
import com.bcp.androidchallenge.presentation.ui.navigation.Navigation

class CurrencyAdapter (val context : Context) : RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

    var itemSelected: Int = -1
    var onItemClicked: ((Int, CurrencyModel) -> Unit)? = null

    var items: List<CurrencyModel> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder =
        CurrencyViewHolder(ItemCurrencyBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val item = items[position]
        holder.bind(createOnClickListener(item),item, holder.adapterPosition)
    }

    private fun createOnClickListener(item :CurrencyModel): View.OnClickListener {

        return View.OnClickListener {
            onItemClicked?.let { it1 -> it1(-1, item) }
        }
    }

    override fun getItemCount() = items.size

    class CurrencyViewHolder(private val binding: ItemCurrencyBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(listener: View.OnClickListener, item: CurrencyModel, adapterPosition :Int ) {

            binding.apply {
                clickListener = listener
                data = item
                executePendingBindings()
            }

        }
    }

}