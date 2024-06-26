package kr.co.main.community.detail

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.co.domain.entity.BulletinEntity
import kr.co.domain.repository.CommunityRepository
import kr.co.ui.base.BaseViewModel
import kr.co.ui.widget.TextAndOnClick
import timber.log.Timber
import javax.inject.Inject

internal interface BulletinDetailEvent {
    fun setIsShowBulletinMoreBottomSheet(boolean: Boolean)
    fun setIsShowDeleteCheckDialog(boolean: Boolean)
    fun setIsShowFailedDialog(boolean: Boolean)
    fun onCommentWritingInput(input: String)
    fun onPostCommentClick()
    fun loadBulletin(id: Long)
    fun deleteBulletin(
        popBackStack: () -> Unit,
        onFail: () -> Unit,
    )

    fun showBottomSheet(bottomSheetItems: List<TextAndOnClick>)
    fun deleteComment(id: Long)

    companion object {
        val dummy = object : BulletinDetailEvent {
            override fun setIsShowBulletinMoreBottomSheet(boolean: Boolean) {}
            override fun setIsShowDeleteCheckDialog(boolean: Boolean) {}
            override fun setIsShowFailedDialog(boolean: Boolean) {}
            override fun onCommentWritingInput(input: String) {}
            override fun onPostCommentClick() {}
            override fun loadBulletin(id: Long) {}
            override fun deleteBulletin(popBackStack: () -> Unit, onFail: () -> Unit) {}
            override fun showBottomSheet(bottomSheetItems: List<TextAndOnClick>) {}
            override fun deleteComment(id: Long) {}
        }
    }

}

@HiltViewModel
internal class BulletinDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val communityRepository: CommunityRepository,
) : BaseViewModel<BulletinDetailViewModel.State>(savedStateHandle), BulletinDetailEvent {

    private val id: Long = savedStateHandle.get<Long>("id") ?: 0L
    override fun createInitialState(savedState: Parcelable?) = State()

    data class State(
        val commentWritingInput: String = "",
        val currentDetailBulletinId: Long = 0L,
        // TODO: 테스트용으로  true. false로 바꿔야댐.
        val isLoadDetailSuccessful: Boolean = true,
        val currentDetailBulletin: BulletinEntity = BulletinEntity.dummy(),
        val currentCategory: BulletinEntity.BulletinCategory = BulletinEntity.BulletinCategory.Free,
        val isShowBulletinMoreBottomSheet: Boolean = false,
        val isShowDeleteCheckDialog: Boolean = false,
        val isShowFailedDialog: Boolean = false,
        val bottomSheetItems: List<TextAndOnClick> = emptyList(),
    ) : BaseViewModel.State

    override fun setIsShowBulletinMoreBottomSheet(boolean: Boolean) =
        updateState { copy(isShowBulletinMoreBottomSheet = boolean) }

    override fun setIsShowDeleteCheckDialog(boolean: Boolean) =
        updateState { copy(isShowDeleteCheckDialog = boolean) }

    override fun setIsShowFailedDialog(boolean: Boolean) =
        updateState { copy(isShowFailedDialog = boolean) }

    override fun onCommentWritingInput(input: String) =
        updateState { copy(commentWritingInput = input) }

    private fun setCurrentDetailBulletinId(id: Long) =
        updateState { copy(currentDetailBulletinId = id) }

    private fun setCurrentDetailBulletin(entity: BulletinEntity) =
        updateState { copy(currentDetailBulletin = entity) }

    //---

    override fun showBottomSheet(bottomSheetItems: List<TextAndOnClick>) {
        updateState {
            copy(
                isShowBulletinMoreBottomSheet = true,
                bottomSheetItems = bottomSheetItems,
            )
        }
    }

    override fun onPostCommentClick() {
        loadingScope {
            val postedCommentId = communityRepository.postComment(
                id = state.value.currentDetailBulletinId,
                commentDetail = state.value.commentWritingInput,
            )
            Timber.d("onPostCommentClick 코루틴) postedCommentId: $postedCommentId")

            // 댓글 달면 글 다시 조회해서 댓글까지 갱신하도록.
            loadBulletin(state.value.currentDetailBulletinId)
        }
    }

    override fun deleteComment(id: Long) {
        loadingScope {
            val resultString = communityRepository.deleteComment(id)
            Timber.d("deleteComment 코루틴) resultString: $resultString")

            // 글 다시 조회해서 댓글까지 갱신하도록.
            loadBulletin(state.value.currentDetailBulletinId)
        }
    }

    override fun loadBulletin(id: Long) {
        viewModelScope.launch {
            setCurrentDetailBulletinId(id)
            Timber.d("loadBulletin 코루틴 시작, id: $id")
            try {
                val entity = communityRepository.getBulletinDetail(id)
                if (entity == null) {
                    updateState { copy(isLoadDetailSuccessful = false) }
                    Timber.d("loadBulletin 코루틴 실패, id: $id")
                } else {
                    updateState { copy(isLoadDetailSuccessful = true) }
                    setCurrentDetailBulletin(entity)
                    Timber.d("loadBulletin 코루틴 성공, id: $id")
                }
            } catch (e: Throwable) {
                Timber.e(e, "loadBulletin 코루틴 에러, id: $id")
                updateState { copy(isLoadDetailSuccessful = false) }
            }
            Timber.d("loadBulletin 코루틴 끝, id: $id")
        }
    }

    override fun deleteBulletin(
        popBackStack: () -> Unit,
        onFail: () -> Unit,
    ) {
        loadingScope {
            val isSuccessful =
                communityRepository.deleteBulletin(state.value.currentDetailBulletinId)
            if (!isSuccessful) {
                onFail()
            } else {
                popBackStack()
            }
        }
    }

    init {
        loadBulletin(id)
    }

}
