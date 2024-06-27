package kr.co.main.my

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kr.co.domain.entity.UserEntity
import kr.co.domain.usecase.user.FetchUserUseCase
import kr.co.domain.usecase.user.UpdateUserUseCase
import kr.co.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class MyPageViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val fetchUserUseCase: FetchUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
) : BaseViewModel<MyPageViewModel.State>(savedStateHandle) {

    fun onCropDeleteClick(crop: String) {
        loadingScope {
            UserEntity(
                crops = currentState.crops?.filter { it != crop }
            ).apply {
                updateUserUseCase(this)
            }
        }.invokeOnCompletion {
            if (it == null)
            updateState {
                copy(
                    crops = currentState.crops?.filter { it != crop }
                )
            }
        }
    }

    init {
        viewModelScopeEH.launch {
            fetchUserUseCase().collectLatest {
                updateState {
                    copy(
                        name = it.name,
                        profileImageUrl = it.profileImage,
                        address = it.address.let { if (it.isNullOrBlank()) "주소를 설정해 주세요" else it },
                        crops = it.crops
                    )
                }
            }
        }
    }
    override fun createInitialState(savedState: Parcelable?) = State()

    data class State(
        val name: String? = null,
        val profileImageUrl: String? = null,
        val address: String? = null,
        val crops: List<String>? = null,
    ) : BaseViewModel.State
}
