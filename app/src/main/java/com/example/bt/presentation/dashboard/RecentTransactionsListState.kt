
package com.example.bt.presentation.dashboard

import com.example.bt.domain.model.Transaction

data class RecentTransactionsListState(
    val list: List<Transaction> = mutableListOf()
)
