package com.hk.transactionsapp.features.transactiondetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.hk.transactionsapp.features.transactions.TransactionsViewModel
import com.hk.transactionsapp.model.TransactionItem
import com.hk.transactionsapp.utils.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TransactionsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private var useCase = FakeTransactionUseCase()

    private lateinit var viewModel: TransactionsViewModel

    @Before
    fun setUp() {
        viewModel = TransactionsViewModel(useCase)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun load_transactions_should_show_loading_and_result() {
        //GIVEN a list of transaction item
        val transactionList = listOf<TransactionItem>(
            generateTransactionItem(),
            generateTransactionItem().copy(id = "2")
        )

        //GIVEN waiting result from api
        mainCoroutineRule.pauseDispatcher()

        //WHEN loading transaction
        viewModel.loadTransactions()

        //THEN return result loading and data return
        viewModel.transactions.observeForTesting {
            assertThat(viewModel.transactions.getOrAwaitValueTest().status)
                .isEqualTo(Resource.Status.LOADING)

            // Execute pending coroutines actions
            mainCoroutineRule.resumeDispatcher()

            useCase.setResourceItems(transactionList)
            val result = viewModel.transactions.getOrAwaitValueTest()
            assertThat(result.status).isEqualTo(Resource.Status.SUCCESS)
            assertThat(result.data).isNotNull()
            assertThat(result.data).hasSize(2)
            assertThat(result.data!![0].id).isEqualTo(transactionList[0].id)
            assertThat(result.data!![1].id).isEqualTo(transactionList[1].id)

        }
    }

    @Test
    @ExperimentalCoroutinesApi
    fun load_transactions_should_show_loading_and_result_empty() {
        // GIVEN we are waiting result from api
        mainCoroutineRule.pauseDispatcher()

        //WHEN loading transaction
        viewModel.loadTransactions()

        //THEN return result loading data and return
        viewModel.transactions.observeForTesting {
            assertThat(viewModel.transactions.getOrAwaitValueTest().status).isEqualTo(
                Resource.Status.LOADING
            )
            // Execute pending coroutines actions
            mainCoroutineRule.resumeDispatcher()
            useCase.setResourceItems(emptyList())
            val result = viewModel.transactions.getOrAwaitValueTest()
            assertThat(result.status).isEqualTo(Resource.Status.SUCCESS)
            assertThat(result.data).isEmpty()

        }

    }

    @ExperimentalCoroutinesApi
    @Test
    fun loadTransactions_should_returnError_if_useCaseReturnError(){
        // GIVEN we are waiting result from api
        mainCoroutineRule.pauseDispatcher()

        //WHEN loading transaction
        viewModel.loadTransactions()

        //THEN return result loading and data return
        viewModel.transactions.observeForTesting {
            assertThat(viewModel.transactions.getOrAwaitValueTest().status).isEqualTo(
                Resource.Status.LOADING
            )

            // Execute pending coroutines actions
            mainCoroutineRule.resumeDispatcher()

            useCase.setReturnError()
            val result = viewModel.transactions.getOrAwaitValueTest()
            assertThat(result.status).isEqualTo(Resource.Status.ERROR)
        }
    }
}