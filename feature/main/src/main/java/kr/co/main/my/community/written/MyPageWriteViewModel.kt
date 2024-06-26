package kr.co.main.my.community.written

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.co.domain.usecase.community.GetMyBulletinsUseCase
import kr.co.domain.usecase.community.GetMyCommentsUseCase
import kr.co.main.mapper.my.MyPageBulletinMapper
import kr.co.main.mapper.my.MyPageCommentMapper
import kr.co.ui.base.BaseViewModel
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
internal class MyPageWriteViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMyBulletinsUseCase: GetMyBulletinsUseCase,
    private val getMyCommentsUseCase: GetMyCommentsUseCase,
) : BaseViewModel<MyPageWriteViewModel.State>(savedStateHandle){

    init {
        viewModelScopeEH.launch {
            getMyBulletinsUseCase().apply {
                updateState {
                    copy(bulletins = map(MyPageBulletinMapper::convert))
                }
            }
        }

        viewModelScopeEH.launch {
            getMyCommentsUseCase().apply {
                updateState {
                    copy(comments = map(MyPageCommentMapper::convert))
                }
            }
        }
    }

    data class State(
        val bulletins : List<Bulletin> = emptyList(),
        val comments : List<Comment> = emptyList(),
    ) : BaseViewModel.State {
        data class Bulletin(
            val id: Long,
            val content: String,
            val thumbnail: String?,
            val createdAt: LocalDateTime,
            val commentCount: Int,
            val bookmarkedCount: Int,
        )

        data class Comment(
            val id: Long,
            val bulletinId: Long,
            val authorName: String,
            val content: String,
            val createAt: LocalDateTime
        )
    }

    override fun createInitialState(savedState: Parcelable?) = State()
}
