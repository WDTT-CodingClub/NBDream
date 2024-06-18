package kr.co.main.accountbook.main

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.co.domain.entity.AccountBookEntity
import kr.co.domain.entity.SortOrder
import kr.co.domain.repository.AccountBookRepository
import kr.co.ui.base.BaseViewModel
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
internal class AccountBookViewModel @Inject constructor(
    private val repository: AccountBookRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<AccountBookViewModel.State>(
    savedStateHandle = savedStateHandle
) {

    fun fetchAccountBooks() {
        viewModelScope.launch {
            try {
                updateState { copy(isLoading = true) }
                val (totalEntity, accountBooks) = repository.getAccountBooks(
                    page = currentState.page,
                    category = currentState.category,
                    sort = currentState.sort,
                    start = currentState.start,
                    end = currentState.end,
                    transactionType = currentState.transactionType?.name ?: ""
                )
                updateState {
                    copy(
                        accountBooks = accountBooks.map {
                            State.AccountBook(
                                id = it.id,
                                title = it.title,
                                day = it.day,
                                dayName = it.dayName,
                                category = it.category.toString(),
                                transactionType = it.transactionType ?: AccountBookEntity.TransactionType.REVENUE,
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

    fun updatePage(newPage: Int) {
        updateState { copy(page = newPage) }
    }

    fun updateCategory(newCategory: String) {
        updateState { copy(category = newCategory) }
    }

    fun updateSortOrder(newSort: String) {
        updateState { copy(sort = newSort) }
    }

    fun updateDateRange(newStart: String, newEnd: String) {
        updateState { copy(start = newStart, end = newEnd) }
    }

    override fun createInitialState(savedState: Parcelable?): State = State()

    data class State(
        val page: Int = 0,
        val category: String = "",
        val sort: String = SortOrder.EARLIEST.name.lowercase(),
        val start: String = LocalDate.now().withDayOfMonth(1).toString(),
        val end: String = LocalDate.now().toString(),
        val transactionType: AccountBookEntity.TransactionType? = null,
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