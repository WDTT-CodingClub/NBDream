package kr.co.main.accountbook.main

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.domain.entity.AccountBookEntity
import kr.co.domain.repository.AccountBookRepository
import kr.co.ui.base.BaseViewModel
import timber.log.Timber
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
            val (totalEntity, newAccountBooks) = repository.getAccountBooks(
                lastContentsId = lastContentsId,
                category = currentState.category,
                sort = currentState.sort.name,
                start = currentState.start,
                end = currentState.end,
                transactionType = currentState.transactionType?.name ?: ""
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
                    hasNext = totalEntity.hasNext
                )
            }
        }
    }

    fun updatePage(lastContentsId: Long) {
        updateState { copy(lastContentsId = lastContentsId) }
        fetchAccountBooks(lastContentsId)
        Timber.d("$lastContentsId")
    }

    fun updateCategory(newCategory: String) {
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

    override fun createInitialState(savedState: Parcelable?): State = State()

    data class State(
        val lastContentsId: Long? = null,
        val category: String = "",
        val sort: AccountBookEntity.SortOrder = AccountBookEntity.SortOrder.EARLIEST,
        val start: String = LocalDate.now().withDayOfMonth(1).toString(),
        val end: String = LocalDate.now().toString(),
        val transactionType: AccountBookEntity.TransactionType? = null,
        val accountBooks: List<AccountBook> = emptyList(),
        val totalCost: Long? = null,
        val totalExpense: Long? = null,
        val totalRevenue: Long? = null,
        val categories: List<String>? = null,
        val hasNext: Boolean? = null
    ) : BaseViewModel.State {
        data class AccountBook(
            val id: Long,
            val title: String?,
            val day: Int?,
            val month: Int?,
            val dayName: String?,
            val category:  AccountBookEntity.Category?,
            val transactionType: AccountBookEntity.TransactionType?,
            val amount: Long? = 0,
            val imageUrl: List<String>
        )
    }
}