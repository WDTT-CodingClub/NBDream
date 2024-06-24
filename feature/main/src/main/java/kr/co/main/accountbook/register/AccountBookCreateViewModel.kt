package kr.co.main.accountbook.register

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kr.co.domain.entity.AccountBookEntity
import kr.co.domain.repository.AccountBookRepository
import kr.co.domain.usecase.image.UploadImageUseCase
import kr.co.main.accountbook.main.formatNumber
import kr.co.ui.base.BaseViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
internal class AccountBookCreateViewModel @Inject constructor(
    private val accountBookRepository: AccountBookRepository,
    private val uploadImageUseCase: UploadImageUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<AccountBookCreateViewModel.State>(savedStateHandle) {
    private val id: Long? = savedStateHandle.get<String>("id")?.toLong()

    init {
        id?.let {
            fetchAccountBookById(it)
        }
    }

    private val _complete: MutableSharedFlow<Unit> = MutableSharedFlow()
    val complete = _complete.asSharedFlow()
    fun updateAmountText(newAmountText: String) {
        updateState { copy(amountText = newAmountText) }
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

    private fun updateImageUrl(url: String) {
        updateState { copy(imageUrls = imageUrls + url) }
    }

    fun uploadImage(image: File) =
        loadingScope {
            uploadImageUseCase(
                UploadImageUseCase.Params(
                    domain = DOMAIN,
                    file = image
                )
            ).let { url ->
                updateImageUrl(url)
            }
        }

    private fun fetchAccountBookById(id: Long) =
        loadingScope {
            val accountBookDetail = accountBookRepository.getAccountBookDetail(id)
            updateState {
                copy(
                    id = accountBookDetail.id,
                    title = accountBookDetail.title ?: "",
                    category = accountBookDetail.category,
                    transactionType = accountBookDetail.transactionType,
                    amount = accountBookDetail.amount ?: 0,
                    registerDateTime = accountBookDetail.registerDateTime ?: "",
                    imageUrls = accountBookDetail.imageUrl
                )
            }
        }.invokeOnCompletion { // 에러 발생 여부
            if (it == null) {
                viewModelScopeEH.launch {

                }
            }
        }

    fun createAccountBook() = loadingScope {
        accountBookRepository.createAccountBook(
            transactionType = currentState.transactionType!!.name.lowercase(),
            amount = currentState.amount,
            category = currentState.category!!.name.lowercase(),
            title = currentState.title ?: "",
            registerDateTime = formatDateTime(currentState.registerDateTime),
            imageUrls = currentState.imageUrls.map { it }
        )
    }.invokeOnCompletion {
        if (it == null) {
            viewModelScopeEH.launch {
                _complete.emit(Unit)
            }
        } else {
            // TODO 작성 실패
        }
    }

    override fun createInitialState(savedState: Parcelable?): State = State()

    data class State(
        val id: Long? = null,
        val amount: Long = 0,
        val amountText: String = formatNumber(amount),
        val transactionType: AccountBookEntity.TransactionType? =
            AccountBookEntity.TransactionType.EXPENSE,
        val category: AccountBookEntity.Category? = null,
        val title: String = "",
        val registerDateTime: String = SimpleDateFormat(
            "yyyy.MM.dd",
            Locale.getDefault()
        ).format(Date()),
        val imageUrls: List<String> = listOf()
    ) : BaseViewModel.State

    private companion object {
        const val DOMAIN = "accountbook"
    }

    private fun formatDateTime(dateTime: String?): String {
        val inputFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd 00:00", Locale.getDefault())
        val date = if (dateTime.isNullOrBlank()) Date() else inputFormat.parse(dateTime)
        return outputFormat.format(date)
    }
}