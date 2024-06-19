package kr.co.main.accountbook.main

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.co.domain.entity.AccountBookEntity
import kr.co.domain.repository.AccountBookRepository
import kr.co.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class AccountBookViewModel @Inject constructor(
    private val repository: AccountBookRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<AccountBookViewModel.State>(savedStateHandle) {

    init {

    }

    fun fetchAccountBooks(
        page: Int,
        category: String,
        sort: String,
        start: String,
        end: String,
        transactionType: String
    ) {
        viewModelScope.launch {
            try {
                updateState { copy(isLoading = true) }
                val (totalEntity, accountBooks) = repository.getAccountBooks(page, category, sort, start, end, transactionType)
                updateState {
                    copy(
                        accountBooks = accountBooks.map {
                            State.AccountBook(
                                id = it.id,
                                title = it.title,
                                day = it.day,
                                dayName = it.dayName,
                                category = it.category.value,
                                transactionType = it.transactionType ?: AccountBookEntity.TransactionType.REVENUE, // Ensure transactionType is not null
                                amount = it.amount ?: 0,
                                imageUrl = it.imageUrl
                            )
                        },
                        totalCost = totalEntity.totalCost,
                        totalExpense = totalEntity.totalExpense,
                        totalRevenue = totalEntity.totalRevenue,
                        categories = totalEntity.categories,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                updateState {
                    copy(
                        errorMessage = e.message,
                        isLoading = false
                    )
                }
            }
        }
    }

    override fun createInitialState(savedState: Parcelable?): State = State()

    data class State(
        val accountBooks: List<AccountBook> = emptyList(),
        val totalCost: Long? = null,
        val totalExpense: Long? = null,
        val totalRevenue: Long? = null,
        val categories: List<String>? = null,
        val isLoading: Boolean = false,
        val errorMessage: String? = null
    ) : BaseViewModel.State {
        data class AccountBook(
            val id: String,
            val title: String,
            val day: Int?,
            val dayName: String?,
            val category: String,
            val transactionType: AccountBookEntity.TransactionType,
            val amount: Long,
            val imageUrl: List<String>
        )
    }
}