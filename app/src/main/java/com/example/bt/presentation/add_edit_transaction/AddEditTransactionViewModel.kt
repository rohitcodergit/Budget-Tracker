

package com.example.bt.presentation.add_edit_transaction

import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bt.domain.model.Transaction
import com.example.bt.domain.repository.TransactionRepository
import com.example.bt.presentation.add_edit_transaction.util.getFormattedTime
import com.example.bt.presentation.util.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTransactionViewModel @Inject constructor(
    private val userDataRepository: TransactionRepository,
    savedStateHandle: SavedStateHandle,

    ) : ViewModel() {

    private val transactionId: Int = checkNotNull(savedStateHandle["id"])
    private val previousScreen: String = checkNotNull(savedStateHandle["previousScreen"])


    private val _title = mutableStateOf(
        AddEditTransactionTextFieldState(
            hint = "Enter a Title.."
        )
    )
    val title: State<AddEditTransactionTextFieldState> = _title

    private val _tags = mutableStateOf(
        AddEditTransactionTextFieldState(
            hint = "Tags"
        )
    )
    val tags: State<AddEditTransactionTextFieldState> = _tags

    private val _amount = mutableStateOf(
        AddEditTransactionTextFieldState(
            hint = "Enter the Amount.."
        )
    )
    val amount: State<AddEditTransactionTextFieldState> = _amount

    private val _note = mutableStateOf(
        AddEditTransactionTextFieldState(
            hint = "Type a note.."
        )
    )
    val note: State<AddEditTransactionTextFieldState> = _note

    private val _transactionType = mutableStateOf(
        AddEditTransactionDropDownMenuState(
            hint = "Transaction Type"
        )
    )
    val transactionType: State<AddEditTransactionDropDownMenuState> = _transactionType

    init {

        if (previousScreen == Screen.TransactionDetails.route) {
            viewModelScope.launch {
                userDataRepository.getTransactionById(transactionId).collect{
                    _title.value = title.value.copy(
                        text = it.title
                    )
                    _amount.value = amount.value.copy(
                        text = it.amount.toString()
                    )
                    _note.value = note.value.copy(
                        text = it.note
                    )
                    _tags.value = tags.value.copy(
                        text = it.tags
                    )
                    _transactionType.value = transactionType.value.copy(
                        selectedOption = it.transactionType
                    )
                    _dialogState.value = dialogState.value.copy(
                        text = "Do you want to discard changes?"
                    )

                }
            }
        }
    }

    private val _dialogState = mutableStateOf(DialogState())
    val dialogState: State<DialogState> = _dialogState


    fun onEvent(event: AddEditTransactionEvent) {
        when {
            event is AddEditTransactionEvent.EnteredTitle -> {
                _title.value = title.value.copy(
                    text = event.value
                )

            }
            event is AddEditTransactionEvent.EnteredAmount -> {
                _amount.value = amount.value.copy(
                    text = event.value
                )
            }
            event is AddEditTransactionEvent.EnteredNote -> {
                _note.value = note.value.copy(
                    text = event.value
                )
            }
            event is AddEditTransactionEvent.OnExpandedChange -> {
                _transactionType.value = transactionType.value.copy(
                    isExpanded = !_transactionType.value.isExpanded
                )
            }
            event is AddEditTransactionEvent.OnDismissRequest -> {
                _transactionType.value = transactionType.value.copy(
                    isExpanded = false
                )
            }
            event is AddEditTransactionEvent.ChangeSelectedOption -> {
                _transactionType.value = transactionType.value.copy(
                    selectedOption = event.value
                )
            }
            event is AddEditTransactionEvent.EnteredTags -> {
                _tags.value = tags.value.copy(
                    text = event.value
                )
            }
            event is AddEditTransactionEvent.SaveEditTransaction -> {
                if (
                    _title.value.text != "" && _tags.value.text != "" && _transactionType.value.selectedOption != "" && _note.value.text != "" && _amount.value.text != ""
                ) {
                    viewModelScope.launch {
                        if (previousScreen == Screen.TransactionDetails.route){
                            userDataRepository.update(
                                Transaction(
                                    id = transactionId,
                                    title = _title.value.text,
                                    amount = _amount.value.text.toLong(),
                                    transactionType = _transactionType.value.selectedOption,
                                    tags = _tags.value.text,
                                    date = getFormattedTime(),
                                    note = _note.value.text


                                )
                            )
                        }else{
                            userDataRepository.insert(
                                Transaction(
                                    title = _title.value.text,
                                    amount = _amount.value.text.toLong(),
                                    transactionType = _transactionType.value.selectedOption,
                                    tags = _tags.value.text,
                                    date = getFormattedTime(),
                                    note = _note.value.text


                                )
                            )
                        }}

                    event.navHostController.navigateUp()

                } else {
                    Toast.makeText(
                        event.context,
                        "Please fill-up all attributes.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            event is AddEditTransactionEvent.OpenDialog -> {
                _dialogState.value = dialogState.value.copy(
                    state = true
                )
            }
            event is AddEditTransactionEvent.CloseDialog -> {
                _dialogState.value = dialogState.value.copy(
                    state = false
                )
            }
        }

    }


}