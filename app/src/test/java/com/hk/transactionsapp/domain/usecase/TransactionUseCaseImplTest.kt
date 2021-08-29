package com.hk.transactionsapp.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.google.common.truth.Truth.assertThat
import com.hk.transactionsapp.domain.repository.TransactionRepositoryImpl
import com.hk.transactionsapp.model.TransactionItem
import com.hk.transactionsapp.utils.Resource
import com.hk.transactionsapp.utils.getOrAwaitValueTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class TransactionUseCaseImplTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var transactionsUseCaseImpl: TransactionsUseCaseImpl

    @Mock
    private lateinit var transactionRepositoryImpl: TransactionRepositoryImpl


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        transactionsUseCaseImpl = TransactionsUseCaseImpl(transactionRepositoryImpl)
    }

    @Test
    fun get_transaction_from_remote_and_validate_not_null() {
        //GIVEN transaction livedata
        val transactionLiveData = generateLiveDataTest(Resource.Status.SUCCESS)
        transactionsUseCaseImpl.transactionsLiveData = transactionLiveData

        //WHEN
        val transactionList = transactionsUseCaseImpl.getTransactions().getOrAwaitValueTest()

        //THEN
        assertThat(transactionList).isNotNull()
    }

    @Test
    fun get_transaction_from_remote_and_validate_status_success() {
        //GIVEN transaction livedata
        val transactionLiveData = generateLiveDataTest(Resource.Status.SUCCESS)
        transactionsUseCaseImpl.transactionsLiveData = transactionLiveData

        //WHEN
        val transactionList = transactionsUseCaseImpl.getTransactions().getOrAwaitValueTest()
        val status = transactionList.status

        //THEN
        assertThat(status).isEqualTo(Resource.Status.SUCCESS)
    }

    @Test
    fun get_transaction_from_remote_and_validate_status_error() {
        //GIVEN transaction livedata
        val transactionLiveData = generateLiveDataTest(Resource.Status.ERROR)
        transactionsUseCaseImpl.transactionsLiveData = transactionLiveData

        //WHEN
        val transactionList = transactionsUseCaseImpl.getTransactions().getOrAwaitValueTest()
        val status = transactionList.status

        //THEN
        assertThat(status).isEqualTo(Resource.Status.ERROR)
    }

    @Test
    fun get_transaction_from_remote_and_validate_status_from_loading_to_success() {
        //GIVEN
        var transactionLiveData = generateLiveDataTest(Resource.Status.LOADING)
        transactionsUseCaseImpl.transactionsLiveData = transactionLiveData

        //WHEN
        var transactionList = transactionsUseCaseImpl.getTransactions().getOrAwaitValueTest()
        var status = transactionList.status
        //THEN
        assertThat(status).isEqualTo(Resource.Status.LOADING)

        //GIVEN
        transactionLiveData = generateLiveDataTest(Resource.Status.SUCCESS)
        transactionsUseCaseImpl.transactionsLiveData = transactionLiveData
        //WHEN
        transactionList = transactionsUseCaseImpl.getTransactions().getOrAwaitValueTest()
        status = transactionList.status
        //THEN
        assertThat(status).isEqualTo(Resource.Status.SUCCESS)
    }


    @Test
    fun get_transaction_from_remote_and_data_return_null() {
        //GIVEN
        val transactionLiveData = generateLiveDataTest(Resource.Status.SUCCESS)
        transactionsUseCaseImpl.transactionsLiveData = transactionLiveData
        //WHEN
        val transactionList = transactionsUseCaseImpl.getTransactions().getOrAwaitValueTest()
        val dataSource = transactionList.data
        //THEN
        assertThat(dataSource).isNull()
    }

    @Test
    fun get_transaction_from_remote_and_data_return_empty() {
        //GIVEN
        val transactionLiveData = generateLiveDataTest(Resource.Status.SUCCESS, false)
        transactionsUseCaseImpl.transactionsLiveData = transactionLiveData
        //WHEN
        val transactionList = transactionsUseCaseImpl.getTransactions().getOrAwaitValueTest()
        val dataSource = transactionList.data
        //THEN
        assertThat(dataSource).isEqualTo(emptyList<TransactionItem>())
    }


    private fun generateLiveDataTest(
        status: Resource.Status,
        isDataNull: Boolean = true
    ): MutableLiveData<Resource<List<TransactionItem>>> {
        val data = MutableLiveData<Resource<List<TransactionItem>>>()
        val resource = Resource<List<TransactionItem>>(status)

        if (isDataNull) {
            resource.data = null
        } else {
            resource.data = generateTransactionItemLiveDataTest()
        }
        data.value = resource
        return data
    }

    private fun generateTransactionItemLiveDataTest(): List<TransactionItem> {
        return mutableListOf()
    }
}