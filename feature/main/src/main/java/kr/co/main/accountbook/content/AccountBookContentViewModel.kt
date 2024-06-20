package kr.co.main.accountbook.content

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.domain.entity.AccountBookEntity
import kr.co.domain.repository.AccountBookRepository
import kr.co.ui.base.BaseViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
internal class AccountBookContentViewModel @Inject constructor(
    private val repository: AccountBookRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<AccountBookContentViewModel.State>(savedStateHandle) {

    fun deleteAccountBookById() {
        state.value.id.let { id ->
            loadingScope {
                repository.deleteAccountBook(id)
            }
        }
    }

    fun fetchAccountBookById(id: Long) {
        loadingScope {
            val accountBookDetail = repository.getAccountBookDetail(id)
            updateState {
                accountBookDetail.id?.let {
                    copy(
                        id = it,
                        title = accountBookDetail.title,
                        category = accountBookDetail.category,
                        transactionType = accountBookDetail.transactionType ?: AccountBookEntity.TransactionType.EXPENSE,
                        amount = accountBookDetail.amount ?: 0,
                        registerDateTime = state.value.registerDateTime,
                        imageUrls = state.value.imageUrls
                    )
                }!!
            }
        }
    }

    override fun createInitialState(savedState: Parcelable?): State = State()

    data class State(
        val id: Long = 0,
        val title: String = "",
        val category: AccountBookEntity.Category = AccountBookEntity.Category.OTHER,
        val transactionType: AccountBookEntity.TransactionType = AccountBookEntity.TransactionType.EXPENSE,
        val amount: Long = 0,
        val registerDateTime: String = SimpleDateFormat(
            "yyyy-MM-dd HH:mm",
            Locale.getDefault()
        ).format(Date()),
        val imageUrls: List<String> = listOf()
    ) : BaseViewModel.State
}
