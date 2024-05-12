
package com.example.bt.data.repository

import com.example.bt.data.data_source.TransactionDao
import com.example.bt.domain.model.Transaction
import com.example.bt.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow


class TransactionRepositoryImpl(
    private val dao: TransactionDao,
) : TransactionRepository {

    override suspend fun insert(transaction: Transaction) {
        dao.insertTransaction(transaction)
    }

    override suspend fun update(transaction: Transaction) {
        dao.updateTransaction(transaction)
    }

    override suspend fun getAllTransactions(): Flow<List<Transaction>> {
        return dao.getAllTransactions()
    }

    override suspend fun getTransactionById(id: Int): Flow<Transaction> {
       return dao.getTransactionById(id)
    }

    override suspend fun deleteTransactionById(id: Int) {
        dao.deleteTransactionById(id)
    }


}