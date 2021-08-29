package com.hk.transactionsapp.features.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<T, V : BaseViewHolder<T>>(private val items: List<T>) :
    RecyclerView.Adapter<V>() {
    override fun onBindViewHolder(holder: V, position: Int) {
        holder.bind(item = getItem(position))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getItem(position: Int): T {
        return items[position]
    }

}


abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: T)
}