package kr.co.main.my.community.bookmark

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.co.domain.entity.BulletinEntity
import kr.co.domain.usecase.community.GetBookmarkUseCase
import kr.co.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class MyPageBookmarkViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getBookmarkUseCase: GetBookmarkUseCase
) : BaseViewModel<MyPageBookmarkViewModel.State>(savedStateHandle){

    init {
        viewModelScopeEH.launch {
            getBookmarkUseCase().let {

            }
        }
    }
    data class State(
        val bulletin: List<BulletinEntity> = emptyList()
    ): BaseViewModel.State

    override fun createInitialState(savedState: Parcelable?) = State()
}
