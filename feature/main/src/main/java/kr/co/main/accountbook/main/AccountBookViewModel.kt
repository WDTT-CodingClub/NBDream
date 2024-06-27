package kr.co.main.accountbook.main

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.domain.entity.AccountBookEntity
import kr.co.domain.repository.AccountBookRepository
import kr.co.main.accountbook.model.DateRangeOption
import kr.co.main.accountbook.model.getEndOfMonth
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

    fun fetchAccountBooks(
        lastContentsId: Long? = null,
        category: String? = null,
        sort: AccountBookEntity.SortOrder? = null,
        transactionType: AccountBookEntity.TransactionType? = null,
    ) {
        loadingScope {
            val (totalEntity, newAccountBooks) = repository.getAccountBooks(
                lastContentsId = lastContentsId,
                category = category ?: currentState.category?.name ?: "",
                sort = sort?.name ?: currentState.sort.name,
                start = currentState.start,
                end = currentState.end,
                transactionType = transactionType?.name ?: (currentState.transactionType?.name
                    ?: "")
            )

            val updatedAccountBooks = if (lastContentsId != null) {
                currentState.accountBooks + newAccountBooks.map {
                    State.AccountBook(
                        id = it.id,
                        title = it.title,
                        day = it.day,
                        month = it.month,
                        dayName = it.dayName,
                        category = it.category,
                        transactionType = it.transactionType,
                        amount = it.amount ?: 0,
                        imageUrl = it.imageUrl
                    )
                }
            } else {
                newAccountBooks.map {
                    State.AccountBook(
                        id = it.id,
                        title = it.title,
                        day = it.day,
                        month = it.month,
                        dayName = it.dayName,
                        category = it.category,
                        transactionType = it.transactionType,
                        amount = it.amount ?: 0,
                        imageUrl = it.imageUrl
                    )
                }
            }

            updateState {
                copy(
                    accountBooks = updatedAccountBooks,
                    totalCost = totalEntity.totalCost,
                    totalExpense = totalEntity.totalExpense,
                    totalRevenue = totalEntity.totalRevenue,
                    categories = totalEntity.categories,
                    revenuePercent = totalEntity.revenuePercent.map {
                        State.PercentCategory(it.percent, it.category)
                    },
                    expensePercent = totalEntity.expensePercent.map {
                        State.PercentCategory(it.percent, it.category)
                    },
                    hasNext = totalEntity.hasNext
                )
            }
        }
    }

    fun refreshItem() = fetchAccountBooks()

    fun updatePage(lastContentsId: Long) {
        if (lastContentsId == currentState.lastContentsId) return
        updateState { copy(lastContentsId = lastContentsId) }
        fetchAccountBooks(lastContentsId)
    }

    fun updateCategory(newCategory: AccountBookEntity.Category?) {
        updateState { copy(category = newCategory) }
        fetchAccountBooks()
    }

    fun updateSortOrder(newSort: AccountBookEntity.SortOrder) {
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

    fun updateDateRangeOption(newDateRangeOption: DateRangeOption) {
        updateState { copy(dateRangeOption = newDateRangeOption) }
        fetchAccountBooks()
    }

    fun updateGraphTransactionType(newTransactionType: AccountBookEntity.TransactionType) {
        updateState { copy(graphTransactionType = newTransactionType) }
    }

    override fun createInitialState(savedState: Parcelable?): State = State()

    data class State(
        val lastContentsId: Long? = null,
        val category: AccountBookEntity.Category? = null,
        val sort: AccountBookEntity.SortOrder = AccountBookEntity.SortOrder.EARLIEST,
        val start: String = LocalDate.now().withDayOfMonth(1).toString(),
        val end: String = getEndOfMonth(),
        val dateRangeOption: DateRangeOption = DateRangeOption.ONE_MONTH,
        val transactionType: AccountBookEntity.TransactionType? = null,
        val accountBooks: List<AccountBook> = emptyList(),
        val totalCost: Long? = null,
        val totalExpense: Long? = null,
        val totalRevenue: Long? = null,
        val categories: List<AccountBookEntity.Category?>? = null,
        val revenuePercent: List<PercentCategory>? = null,
        val expensePercent: List<PercentCategory>? = null,
        val graphTransactionType: AccountBookEntity.TransactionType = AccountBookEntity.TransactionType.EXPENSE,
        val hasNext: Boolean? = null
    ) : BaseViewModel.State {
        data class AccountBook(
            val id: Long,
            val title: String?,
            val day: Int?,
            val month: Int?,
            val dayName: String?,
            val category: AccountBookEntity.Category?,
            val transactionType: AccountBookEntity.TransactionType?,
            val amount: Long? = 0,
            val imageUrl: List<String>?
        )

        data class PercentCategory(
            val percent: Float,
            val category: AccountBookEntity.Category
        )
    }
}
