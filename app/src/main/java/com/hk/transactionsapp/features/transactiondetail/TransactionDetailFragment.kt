package com.hk.transactionsapp.features.transactiondetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.hk.transactionsapp.R
import com.hk.transactionsapp.databinding.FragmentTransactionDetailBinding
import com.hk.transactionsapp.model.TransactionItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionDetailFragment : Fragment() {

    private val transactionDetailViewModel: TransactionDetailViewModel by viewModels()


    private lateinit var fragmentTransactionDetailBinding: FragmentTransactionDetailBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentTransactionDetailBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_transaction_detail,
            container,
            false
        )
        return fragmentTransactionDetailBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val transactionItem: TransactionItem? = arguments?.getParcelable("transactionsItem")

        transactionDetailViewModel.transactionItem.postValue(
            transactionDetailViewModel.convertTransactionToDetail(transactionItem!!)
        )

        transactionDetailViewModel.transactionItem.observe(viewLifecycleOwner, {
            fragmentTransactionDetailBinding.transactionItem = it
        })


    }
}