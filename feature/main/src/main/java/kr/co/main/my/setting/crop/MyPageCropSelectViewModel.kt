package kr.co.main.my.setting.crop

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kr.co.domain.entity.UserEntity
import kr.co.domain.usecase.user.FetchUserUseCase
import kr.co.domain.usecase.user.UpdateUserUseCase
import kr.co.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class MyPageCropSelectViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val fetchUserUseCase: FetchUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
): BaseViewModel<MyPageCropSelectViewModel.State>(savedStateHandle) {

    private val _showMyPage = MutableSharedFlow<Unit>()
    val showMyPage = _showMyPage.asSharedFlow()

    fun onCropSelected(crop: String) = updateState {
        if (crops.contains(crop)) {
            copy(crops = crops - crop)
        } else {
            copy(crops = crops + crop)
        }
    }

    fun onConfirmClick() {
        viewModelScopeEH.launch {
            UserEntity(
                crops = currentState.crops
            ).apply {
                updateUserUseCase(this)
            }
        }.invokeOnCompletion {
            if (it == null)
                viewModelScopeEH.launch {
                    _showMyPage.emit(Unit)
                }
        }
    }

    init {
        viewModelScopeEH.launch {
            fetchUserUseCase().collect {
                updateState {
                    copy(crops = it.crops?: emptyList())
                }
            }
        }
    }

    data class State(
        val crops: List<String> = emptyList()
    ): BaseViewModel.State

    override fun createInitialState(savedState: Parcelable?) = State()
}
