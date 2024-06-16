package kr.co.main.accountbook.content

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.domain.entity.AccountBookEntity
import kr.co.domain.repository.AccountBookRepository
import kr.co.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class AccountBookContentViewModel @Inject constructor(
    private val repository: AccountBookRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<AccountBookContentViewModel.State>(savedStateHandle) {

    init {

    }

    fun fetchAccountBookById(id: String) {
        loadingScope {
            try {
                val accountBookDetail = repository.getAccountBookDetail(id)
                updateState {
                    copy(
                        id = accountBookDetail.id,
                        title = accountBookDetail.title,
                        category = accountBookDetail.category,
                        year = accountBookDetail.year ?: 0,
                        month = accountBookDetail.month ?: 0,
                        day = accountBookDetail.day ?: 0,
                        transactionType = accountBookDetail.transactionType ?: AccountBookEntity.TransactionType.EXPENSE,
                        amount = accountBookDetail.amount ?: 0
                    )
                }
            } catch (e: Exception) {
                // TODO Error
            }
        }
    }

    override fun createInitialState(savedState: Parcelable?): State = State()

    data class State(
        val id: String = "",
        val title: String = "",
        val category: AccountBookEntity.Category = AccountBookEntity.Category.OTHER,
        val year: Int = 0,
        val month: Int = 0,
        val day: Int = 0,
        val transactionType: AccountBookEntity.TransactionType = AccountBookEntity.TransactionType.EXPENSE,
        val amount: Long = 0
    ) : BaseViewModel.State
}
