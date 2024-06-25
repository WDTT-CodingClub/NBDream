package kr.co.main.my.setting.delete

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.co.domain.usecase.DeleteAccountUseCase
import kr.co.domain.usecase.PostDeleteReasonUseCase
import kr.co.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class MyPageSettingDeleteAccountViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val postDeleteReasonUseCase: PostDeleteReasonUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase
) : BaseViewModel<MyPageSettingDeleteAccountViewModel.State>(savedStateHandle) {

    fun onSelectChange(e: String?) = updateState {
        copy(reason = e)
    }

    fun onReasonChange(e: String) = updateState {
        copy(otherReason = e)
    }

    fun onDeleteClick() {
        viewModelScopeEH.launch {
            PostDeleteReasonUseCase.Params(
                reason = currentState.reason!!,
                otherReason = currentState.otherReason
            ).run {
                postDeleteReasonUseCase(this)
            }
            deleteAccountUseCase()
        }
    }
    override fun createInitialState(savedState: Parcelable?) = State()

    data class State (
        val reason: String? = null,
        val otherReason: String? = null
    ) : BaseViewModel.State {
        val isSelectValid: Boolean
            get() = reason != null
    }

}
