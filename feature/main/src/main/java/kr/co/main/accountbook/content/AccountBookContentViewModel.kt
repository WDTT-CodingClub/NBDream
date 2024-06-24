package kr.co.main.accountbook.content

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.co.domain.entity.AccountBookEntity
import kr.co.domain.repository.AccountBookRepository
import kr.co.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class AccountBookContentViewModel @Inject constructor(
    private val repository: AccountBookRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<AccountBookContentViewModel.State>(savedStateHandle) {
    private val id: Long = checkNotNull(savedStateHandle.get<String>("id")?.toLong())

    init {
        fetchAccountBookById(id)
    }

    fun deleteAccountBookById() =
        state.value.id.let { id ->
            loadingScope {
                repository.deleteAccountBook(id)
            }
        }

    private fun fetchAccountBookById(id: Long) =
        loadingScope {
            val accountBookDetail = repository.getAccountBookDetail(id)
            updateState {
                copy(
                    id = accountBookDetail.id,
                    title = accountBookDetail.title,
                    category = accountBookDetail.category,
                    transactionType = accountBookDetail.transactionType,
                    amount = accountBookDetail.amount ?: 0,
                    registerDateTime = accountBookDetail.registerDateTime,
                    imageUrls = accountBookDetail.imageUrl
                )
            }
        }.invokeOnCompletion { // 에러 발생 여부
            if (it == null) {
                viewModelScopeEH.launch {

                }
            }
        }

    override fun createInitialState(savedState: Parcelable?): State = State()

    data class State(
        val id: Long = 0,
        val title: String? = null,
        val category: AccountBookEntity.Category? = null,
        val transactionType: AccountBookEntity.TransactionType? = null,
        val amount: Long = 0,
        val registerDateTime: String? = null,
        val imageUrls: List<String> = emptyList()
    ) : BaseViewModel.State
}
