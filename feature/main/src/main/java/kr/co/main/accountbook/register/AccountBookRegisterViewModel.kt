package kr.co.main.accountbook.register

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kr.co.domain.entity.AccountBookEntity
import kr.co.domain.repository.AccountBookRepository
import kr.co.domain.repository.ServerImageRepository
import kr.co.ui.base.BaseViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
internal class AccountBookRegisterViewModel @Inject constructor(
    private val accountBookRepository: AccountBookRepository,
    private val serverImageRepository: ServerImageRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<AccountBookRegisterViewModel.State>(savedStateHandle) {

    private val _snackBarMessage = MutableStateFlow<String?>(null)
    val snackBarMessage: StateFlow<String?> get() = _snackBarMessage

    fun resetSnackBarState() {
        _snackBarMessage.value = null
    }

    fun updateAmount(amount: Long) {
        updateState { copy(amount = amount) }
    }

    fun updateTransactionType(transactionType: AccountBookEntity.TransactionType) {
        updateState { copy(transactionType = transactionType) }
    }

    fun updateCategory(category: AccountBookEntity.Category) {
        updateState { copy(category = category) }
    }

    fun updateTitle(title: String) {
        updateState { copy(title = title) }
    }

    fun updateRegisterDateTime(registerDateTime: String) {
        updateState { copy(registerDateTime = registerDateTime) }
    }

    fun removeImageUrl(uri: String) {
        updateState { copy(imageUrls = imageUrls - uri) }
    }

    fun uploadImage(file: File) {
        // TODO isLoading 관찰
        loadingScope {
            val imageUrl = serverImageRepository.upload("accountbook", file)?.url
                ?: throw Exception("Image upload failed")
            updateState { copy(imageUrls = imageUrls + imageUrl) }
        }
    }

    fun validationCreateAccountBook() {
        when {
            state.value.amount == 0L -> {
                _snackBarMessage.value = "금액을 입력하세요"
            }

            state.value.transactionType == null -> {
                _snackBarMessage.value = "분류를 선택하세요"
            }

            state.value.category == null -> {
                _snackBarMessage.value = "카테고리를 선택하세요"
            }

            else -> {
                createAccountBook()
            }
        }
    }

    private fun createAccountBook() {
        loadingScope {
            accountBookRepository.createAccountBook(
                transactionType = state.value.transactionType!!.name.lowercase(),
                amount = state.value.amount,
                category = state.value.category!!.name.lowercase(),
                title = state.value.title,
                registerDateTime = state.value.registerDateTime,
                imageUrls = state.value.imageUrls.map { it }
            )
        }
    }

    override fun createInitialState(savedState: Parcelable?): State = State()

    data class State(
        val amount: Long = 0,
        val transactionType: AccountBookEntity.TransactionType? =
            AccountBookEntity.TransactionType.EXPENSE,
        val category: AccountBookEntity.Category? = null,
        val title: String = "",
        val registerDateTime: String = SimpleDateFormat(
            "yyyy-MM-dd HH:mm",
            Locale.getDefault()
        ).format(Date()),
        val imageUrls: List<String> = listOf()
    ) : BaseViewModel.State
}