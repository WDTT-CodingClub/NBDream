package kr.co.main.accountbook.create

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
import kr.co.main.accountbook.model.DATE_FORMAT_PATTERN
import kr.co.main.accountbook.model.EntryType
import kr.co.main.accountbook.model.formatReceiveDateTime
import kr.co.main.accountbook.model.formatSendDateTime
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
    private val entryType: EntryType = savedStateHandle.get<EntryType>("entryType") ?: EntryType.CREATE

    private val _complete: MutableSharedFlow<Unit> = MutableSharedFlow()
    val complete = _complete.asSharedFlow()

    init {
        id?.let {
            fetchAccountBookById(it)
        }
    }

    fun updateAmountText(newAmountText: String) {
        updateState { copy(amountText = newAmountText) }
    }

    fun updateAmount(amount: Long) {
        updateState {
            copy(
                amount = amount,
                amountText = formatNumber(amount)
            )
        }
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

    private fun updateAccountBook() = loadingScope {
        currentState.id?.let {
            accountBookRepository.updateAccountBook(
                id = it,
                transactionType = currentState.transactionType!!.name.lowercase(),
                amount = currentState.amount ?: 0L,
                category = currentState.category!!.name.lowercase(),
                title = currentState.title ?: "",
                registerDateTime = currentState.registerDateTime,
                imageUrls = currentState.imageUrls
            )
        }
    }.invokeOnCompletion {
        if (it == null) {
            viewModelScopeEH.launch {
                _complete.emit(Unit)
            }
        } else {
            // TODO 작성 실패
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
                    registerDateTime = accountBookDetail.registerDateTime?.formatReceiveDateTime() ?: "",
                    imageUrls = accountBookDetail.imageUrl,
                    amountText = formatNumber(accountBookDetail.amount ?: 0)
                )
            }
        }


    fun createAccountBook() = loadingScope {
        accountBookRepository.createAccountBook(
            transactionType = currentState.transactionType!!.name.lowercase(),
            amount = currentState.amount ?: 0L,
            category = currentState.category!!.name.lowercase(),
            title = currentState.title ?: "",
            registerDateTime = currentState.registerDateTime.formatSendDateTime(),
            imageUrls = currentState.imageUrls
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
        val amount: Long? = 0,
        val amountText: String = formatNumber(amount ?: 0),
        val transactionType: AccountBookEntity.TransactionType? =
            AccountBookEntity.TransactionType.EXPENSE,
        val category: AccountBookEntity.Category? =
            AccountBookEntity.Category.OTHER,
        val title: String? = null,
        val registerDateTime: String = SimpleDateFormat(
            DATE_FORMAT_PATTERN,
            Locale.getDefault()
        ).format(Date()),
        val imageUrls: List<String> = listOf()
    ) : BaseViewModel.State

    private companion object {
        const val DOMAIN = "accountbook"
    }
}