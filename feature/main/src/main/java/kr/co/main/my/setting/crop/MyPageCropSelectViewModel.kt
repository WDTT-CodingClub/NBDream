package kr.co.main.my.setting.crop

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kr.co.domain.entity.UserEntity
import kr.co.domain.usecase.user.FetchUserUseCase
import kr.co.domain.usecase.user.RegisterUserUseCase
import kr.co.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class MyPageCropSelectViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val fetchUserUseCase: FetchUserUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
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
                name = currentState.name,
                crops = currentState.crops
            ).apply {
                registerUserUseCase(this)
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
                    copy(name = it.name)
                    copy(crops = it.crops?: emptyList())
                }
            }
        }
    }

    data class State(
        val name: String = "",
        val crops: List<String> = emptyList()
    ): BaseViewModel.State

    override fun createInitialState(savedState: Parcelable?) = State()
}
