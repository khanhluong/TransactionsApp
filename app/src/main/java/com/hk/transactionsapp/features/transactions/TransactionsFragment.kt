package com.hk.transactionsapp.features.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hk.transactionsapp.R
import com.hk.transactionsapp.databinding.FragmentTransactionsBinding
import com.hk.transactionsapp.model.TransactionItem
import com.hk.transactionsapp.utils.Resource
import com.hk.transactionsapp.utils.convertDate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TransactionsFragment : Fragment(R.layout.fragment_transactions),
    TransactionsAdapter.TransactionItemListener {

    private lateinit var fragmentTransactionsBinding: FragmentTransactionsBinding
    private val viewModel: TransactionsViewModel by activityViewModels()
    private lateinit var transactionsAdapter: TransactionsAdapter
    private val transactions: MutableList<TransactionItem> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentTransactionsBinding =
            FragmentTransactionsBinding.inflate(inflater, container, false)
        return fragmentTransactionsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
    }

    private fun setupRecyclerView() {
        transactions.sortBy { convertDate(it.transactionDate) }
        transactionsAdapter = TransactionsAdapter(transactions, this)
        fragmentTransactionsBinding.rvTransaction.layoutManager =
            LinearLayoutManager(requireContext())
        fragmentTransactionsBinding.rvTransaction.adapter = transactionsAdapter
    }

    private fun setupObservers() {
        viewModel.transactions.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    fragmentTransactionsBinding.progressBar.visibility = View.GONE
                    transactions.clear()
                    transactions.addAll(it.data!!)
                    setupRecyclerView()
                }
                Resource.Status.ERROR -> Toast.makeText(
                    requireContext(),
                    it.message,
                    Toast.LENGTH_SHORT
                ).show()
                Resource.Status.LOADING -> {
                    fragmentTransactionsBinding.progressBar.visibility = View.VISIBLE
                }
            }
        })
    }


    override fun onClickTransaction(transactionItem: TransactionItem) {
        findNavController()
            .navigate(
                R.id.action_transactionFragment_to_transactionDetailFragment,
                bundleOf(
                    "transactionsItem" to transactionItem
                )
            )
    }
}