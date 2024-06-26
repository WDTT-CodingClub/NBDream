package kr.co.main.accountbook.create

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kr.co.domain.entity.AccountBookEntity
import kr.co.domain.repository.AccountBookRepository
import kr.co.domain.usecase.image.DeleteImageUseCase
import kr.co.domain.usecase.image.UploadImageUseCase
import kr.co.main.accountbook.model.DATE_FORMAT_PATTERN
import kr.co.main.accountbook.model.EntryType
import kr.co.main.accountbook.model.formatNumber
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
    private val deleteImageUseCase: DeleteImageUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<AccountBookCreateViewModel.State>(savedStateHandle) {
    private val id: Long? = savedStateHandle.get<String>("id")?.toLong()
    private val _entryType: EntryType = savedStateHandle.get<String>("entryType")?.let {
        when (it) {
            EntryType.CREATE.name -> EntryType.CREATE
            EntryType.UPDATE.name -> EntryType.UPDATE
            else -> EntryType.CREATE
        }
    } ?: EntryType.CREATE
    val entryType: EntryType get() = _entryType

    private val _complete: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val complete = _complete.asSharedFlow()

    init {
        id?.let {
            fetchAccountBookById(it)
        }
        updateButtonText()
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

    private fun removeImageUrl(uri: String) {
        updateState { copy(newImageUrls = newImageUrls - uri) }
    }

    private fun updateImageUrl(url: String) {
        updateState { copy(newImageUrls = newImageUrls + url) }
    }

    private fun updateButtonText() {
        updateState { copy(buttonText = if (_entryType == EntryType.UPDATE) "수정" else "등록") }
    }

    fun deleteImage(url: String) =
        loadingScope {
            deleteImageUseCase(url)
        }.invokeOnCompletion {
            if (it == null) {
                removeImageUrl(url)
            }
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

    fun performAccountBook() =
        if (entryType == EntryType.UPDATE) {
            updateAccountBook()
        } else {
            createAccountBook()
        }


    private fun updateAccountBook() = loadingScope {
        currentState.id?.let {
            accountBookRepository.updateAccountBook(
                id = it,
                transactionType = currentState.transactionType!!.name.lowercase(),
                amount = currentState.amount ?: 0L,
                category = currentState.category!!.name.lowercase(),
                title = currentState.title ?: "",
                registerDateTime = currentState.registerDateTime.formatSendDateTime(),
                imageUrls = getImageList()
            )
        }
    }.invokeOnCompletion { throwable ->
        viewModelScopeEH.launch {
            _complete.emit(
                throwable == null
            )
        }
    }

    private fun getImageList(): List<String> {
        val imageUrls = currentState.imageUrls
        val newImageUrls = currentState.newImageUrls.toMutableList()

        imageUrls.forEach { imageUrl ->
            if (!newImageUrls.contains(imageUrl)) {
                newImageUrls.add(imageUrl)
            }
        }

        newImageUrls.removeAll { imageUrl ->
            imageUrls.contains(imageUrl)
        }
        return newImageUrls
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
                    registerDateTime = accountBookDetail.registerDateTime?.formatReceiveDateTime()
                        ?: "",
                    imageUrls = accountBookDetail.imageUrl,
                    newImageUrls = accountBookDetail.imageUrl,
                    amountText = formatNumber(accountBookDetail.amount ?: 0)
                )
            }
        }


    private fun createAccountBook() = loadingScope {
        accountBookRepository.createAccountBook(
            transactionType = currentState.transactionType!!.name.lowercase(),
            amount = currentState.amount ?: 0L,
            category = currentState.category!!.name.lowercase(),
            title = currentState.title ?: "",
            registerDateTime = currentState.registerDateTime.formatSendDateTime(),
            imageUrls = getImageList()
        )
    }.invokeOnCompletion { throwable ->
        viewModelScopeEH.launch {
            _complete.emit(
                throwable == null
            )
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
        val imageUrls: List<String> = listOf(),
        val newImageUrls: List<String> = listOf(),
        val buttonText: String = "등록"
    ) : BaseViewModel.State

    private companion object {
        const val DOMAIN = "accountbook"
    }
}