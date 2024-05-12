

package com.example.bt.presentation.transactions

import com.example.bt.domain.model.Transaction

data class TransactionsState(
    val list: List<Transaction> = mutableListOf()
)