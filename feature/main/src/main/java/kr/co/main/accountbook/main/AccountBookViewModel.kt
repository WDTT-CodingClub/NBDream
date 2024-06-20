package kr.co.main.accountbook.main

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
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

    init {
        fetchAccountBooks()
    }

    private fun fetchAccountBooks(lastContentsId: Long? = null) {
        loadingScope {
            val (totalEntity, accountBooks) = repository.getAccountBooks(
                lastContentsId = lastContentsId,
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
                            category = it.category?.name,
                            transactionType = it.transactionType,
                            amount = it.amount ?: 0,
                            imageUrl = it.imageUrl
                        )
                    },
                    totalCost = totalEntity.totalCost,
                    totalExpense = totalEntity.totalExpense,
                    totalRevenue = totalEntity.totalRevenue,
                    categories = totalEntity.categories,
                )
            }
        }
    }


    fun updatePage(lastContentsId: Long) {
        updateState { copy(lastContentsId = lastContentsId) }
        fetchAccountBooks(lastContentsId)
    }

    fun updateCategory(newCategory: String) {
        updateState { copy(category = newCategory) }
        fetchAccountBooks()
    }

    fun updateSortOrder(newSort: String) {
        updateState { copy(sort = newSort) }
        fetchAccountBooks()
    }

    fun updateDateRange(newStart: String, newEnd: String) {
        updateState { copy(start = newStart, end = newEnd) }
        fetchAccountBooks()
    }

    fun updateTransactionType(newTransactionType: AccountBookEntity.TransactionType?) {
        updateState { copy(transactionType = newTransactionType) }
        fetchAccountBooks()
    }

    override fun createInitialState(savedState: Parcelable?): State = State()

    data class State(
        val lastContentsId: Long? = null,
        val category: String = "",
        val sort: String = SortOrder.EARLIEST.name.lowercase(),
        val start: String = LocalDate.now().withDayOfMonth(1).toString(),
        val end: String = LocalDate.now().toString(),
        val transactionType: AccountBookEntity.TransactionType? = null,
        val accountBooks: List<AccountBook> = emptyList(),
        val totalCost: Long? = null,
        val totalExpense: Long? = null,
        val totalRevenue: Long? = null,
        val categories: List<String>? = null
    ) : BaseViewModel.State {
        data class AccountBook(
            val id: Long,
            val title: String?,
            val day: Int?,
            val dayName: String?,
            val category: String?,
            val transactionType: AccountBookEntity.TransactionType?,
            val amount: Long? = 0,
            val imageUrl: List<String>
        )
    }
}