
package com.example.bt.presentation.util

sealed class Screen(val route: String) {
    object Dashboard : Screen(route = "dashboard")
    object TransactionDetails : Screen(route = "transaction_details")
    object AddEditTransaction : Screen(route = "add_transaction")
    object Transactions : Screen(route = "transactions")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
