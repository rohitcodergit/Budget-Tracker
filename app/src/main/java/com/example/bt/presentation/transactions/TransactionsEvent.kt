

package com.example.bt.presentation.transactions

sealed class TransactionsEvent {
    object OnExpandedChange : TransactionsEvent()
    object OnDismissRequest : TransactionsEvent()
    data class ChangeSelectedOption(val value: String) : TransactionsEvent()
}
