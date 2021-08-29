package com.hk.transactionsapp.features.transactions

import com.google.common.truth.Truth
import com.hk.transactionsapp.features.transactiondetail.TransactionDetailViewModel
import com.hk.transactionsapp.utils.MainCoroutineRule
import com.hk.transactionsapp.utils.convertDate
import com.hk.transactionsapp.utils.generateTransactionItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TransactionDetailViewModelTest {

    private lateinit var viewModel: TransactionDetailViewModel

    @Before
    fun setUp() {
        viewModel = TransactionDetailViewModel()
    }

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    @ExperimentalCoroutinesApi
    @Test
    fun getTransactionsItemToDetail_returnValidDetailList_when_transactionItemIsValid() {

        // GIVEN a valid transaction item
        val transactionItem = generateTransactionItem()

        mainCoroutineRule.pauseDispatcher()

        // WHEN convert it to detail
        val details = viewModel.convertTransactionToDetail(transactionItem)

        // THEN validate

        Truth.assertThat(details.id)
            .isEqualTo(transactionItem.id)
        Truth.assertThat(details.credit)
            .isEqualTo(transactionItem.credit)
        Truth.assertThat(details.debit)
            .isEqualTo(transactionItem.debit)
        Truth.assertThat(details.summary)
            .isEqualTo(transactionItem.summary)
        Truth.assertThat(details.transactionDate)
            .isEqualTo(convertDate(transactionItem.transactionDate))
    }
}