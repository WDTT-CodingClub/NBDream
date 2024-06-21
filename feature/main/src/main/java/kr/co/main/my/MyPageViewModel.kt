package kr.co.main.my

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kr.co.domain.entity.UserEntity
import kr.co.domain.usecase.user.FetchUserUseCase
import kr.co.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class MyPageViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val fetchUserUseCase: FetchUserUseCase,
) : BaseViewModel<MyPageViewModel.State>(savedStateHandle) {


    init {
        viewModelScopeEH.launch {
            fetchUserUseCase().collectLatest {
                updateState {
                    copy(
                        name = it.name,
                        profileImageUrl = it.profileImage,
                        address = it.address,
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
