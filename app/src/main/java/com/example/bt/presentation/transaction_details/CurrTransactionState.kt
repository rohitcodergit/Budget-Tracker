

package com.example.bt.presentation.transaction_details

import com.example.bt.domain.model.Transaction


data class CurrTransactionState(
    val transaction: Transaction? = Transaction(
        id = -1,
        transactionType = "",
        title = "",
        amount = 0,
        tags = "",
        date = "",
        note = ""
    )

)