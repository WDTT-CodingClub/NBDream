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
internal class AccountBookRegisterViewModel @Inject constructor(
    private val accountBookRepository: AccountBookRepository,
    private val uploadImageUseCase: UploadImageUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<AccountBookRegisterViewModel.State>(savedStateHandle) {
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

    fun createAccountBook() = loadingScope {
        accountBookRepository.createAccountBook(
            transactionType = state.value.transactionType!!.name.lowercase(),
            amount = state.value.amount,
            category = state.value.category!!.name.lowercase(),
            title = state.value.title,
            registerDateTime = "${state.value.registerDateTime} 00:00",
            imageUrls = state.value.imageUrls.map { it }
        )
    }.invokeOnCompletion {
        if (it == null) {
            viewModelScopeEH.launch {
                _complete.emit(Unit)
            }
        }
    }

    override fun createInitialState(savedState: Parcelable?): State = State()

    data class State(
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
}