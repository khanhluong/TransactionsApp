package com.hk.transactionsapp.features.transactions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hk.transactionsapp.databinding.ItemTransactionBinding
import com.hk.transactionsapp.features.base.BaseRecyclerAdapter
import com.hk.transactionsapp.features.base.BaseViewHolder
import com.hk.transactionsapp.model.TransactionItem
import com.hk.transactionsapp.utils.convertDate

class TransactionsAdapter(
    items: List<TransactionItem>,
    private val listener: TransactionItemListener
) : BaseRecyclerAdapter<TransactionItem, TransactionsViewHolder>(items) {

    interface TransactionItemListener {
        fun onClickTransaction(transactionItem: TransactionItem)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionsViewHolder {
        val binding: ItemTransactionBinding =
            ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionsViewHolder(binding, listener)
    }

}

class TransactionsViewHolder(
    private val itemTransactionBinding: ItemTransactionBinding,
    private val listener: TransactionsAdapter.TransactionItemListener
) : BaseViewHolder<TransactionItem>(itemTransactionBinding.root), View.OnClickListener {

    init {
        itemView.setOnClickListener(this)
    }

    private lateinit var transactionItem: TransactionItem

    override fun bind(item: TransactionItem) {
        this.transactionItem = item
        itemTransactionBinding.tvTransactionId.text = item.id
        itemTransactionBinding.tvTransactionDate.text = convertDate(item.transactionDate)
        itemTransactionBinding.tvTransactionSummary.text = item.summary
        itemTransactionBinding.tvMoneySpent.text = moneySpent(item.debit, item.credit)
    }

    override fun onClick(v: View?) {
        listener.onClickTransaction(transactionItem)
    }

    private fun moneySpent(debit: Double, credit: Double): String {
        return if (debit != 0.0) {
            debit.toString()
        } else {
            credit.toString()
        }
    }


}