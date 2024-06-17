package kr.co.main.accountbook.register

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.domain.entity.AccountBookEntity
import kr.co.domain.repository.AccountBookRepository
import kr.co.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class AccountBookRegisterViewModel @Inject constructor(
    private val repository: AccountBookRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<AccountBookRegisterViewModel.State>(savedStateHandle) {
    override fun createInitialState(savedState: Parcelable?): State = State()

    data class State(
        val title: String = "",
        val category: AccountBookEntity.Category = AccountBookEntity.Category.OTHER,
        val year: Int = 0,
        val month: Int = 0,
        val day: Int = 0,
        val revenue: Long = 0,
        val expense: Long = 0
    ) : BaseViewModel.State
}