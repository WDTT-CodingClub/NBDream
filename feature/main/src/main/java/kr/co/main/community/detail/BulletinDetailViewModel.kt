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
    fun setIsShowDeleteCheckDialog(boolean: Boolean)
    fun setIsShowFailedDialog(boolean: Boolean)
    fun setIsShowDialog(boolean: Boolean)
    fun onCommentWritingInput(input: String)
    fun onPostCommentClick()
    fun loadBulletin(id: Long)
    fun deleteBulletin(
        popBackStack: () -> Unit,
        onFail: () -> Unit,
    )

    fun deleteComment(id: Long)
    fun showDialog(
        header: String,
        description: String,
        onConfirm: () -> Unit,
        onDismiss: () -> Unit,
    )

    fun bookmarkBulletin()
    fun showReportBottomSheet()

    // DreamBottomSheetWithTextButtons
    fun setIsShowDreamBottomSheetWithTextButtons(boolean: Boolean)
    fun showBottomSheet(bottomSheetItems: List<TextAndOnClick>)

    // simple dialog
    fun setIsShowSimpleDialog(boolean: Boolean)
    fun showSimpleDialog(text: String)

    companion object {
        val empty = object : BulletinDetailEvent {
            override fun setIsShowDreamBottomSheetWithTextButtons(boolean: Boolean) {}
            override fun setIsShowDeleteCheckDialog(boolean: Boolean) {}
            override fun setIsShowFailedDialog(boolean: Boolean) {}
            override fun setIsShowDialog(boolean: Boolean) {}
            override fun onCommentWritingInput(input: String) {}
            override fun onPostCommentClick() {}
            override fun loadBulletin(id: Long) {}
            override fun deleteBulletin(popBackStack: () -> Unit, onFail: () -> Unit) {}
            override fun showBottomSheet(bottomSheetItems: List<TextAndOnClick>) {}
            override fun deleteComment(id: Long) {}
            override fun showDialog(
                header: String,
                description: String,
                onConfirm: () -> Unit,
                onDismiss: () -> Unit,
            ) {
            }

            override fun bookmarkBulletin() {}
            override fun setIsShowSimpleDialog(boolean: Boolean) {}
            override fun showSimpleDialog(text: String) {}
            override fun showReportBottomSheet() {}

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
        val currentDetailBulletin: BulletinEntity = BulletinEntity.empty(),
        val currentCategory: BulletinEntity.BulletinCategory = BulletinEntity.BulletinCategory.Free,
        val isShowDeleteCheckDialog: Boolean = false,
        val isShowFailedDialog: Boolean = false,
        val isInitialLoadingFinished: Boolean = false,

        // bottom sheet
        val isShowDreamBottomSheetWithTextButtons: Boolean = false,
        val bottomSheetItems: List<TextAndOnClick> = emptyList(),

        // dream dialog
        val isShowDialog: Boolean = false,
        val dialogHeader: String = "dialogHeader",
        val dialogDescription: String = "dialogDescription",
        val dialogOnConfirm: () -> Unit = {},
        val dialogOnDismiss: () -> Unit = {},

        // simple dialog
        val isShowSimpleDialog: Boolean = false,
        val simpleDialogText: String = "",
    ) : BaseViewModel.State

    override fun setIsShowSimpleDialog(boolean: Boolean) =
        updateState { copy(isShowSimpleDialog = boolean) }

    override fun setIsShowDreamBottomSheetWithTextButtons(boolean: Boolean) =
        updateState { copy(isShowDreamBottomSheetWithTextButtons = boolean) }

    override fun setIsShowDeleteCheckDialog(boolean: Boolean) =
        updateState { copy(isShowDeleteCheckDialog = boolean) }

    override fun setIsShowFailedDialog(boolean: Boolean) =
        updateState { copy(isShowFailedDialog = boolean) }

    override fun setIsShowDialog(boolean: Boolean) =
        updateState { copy(isShowDialog = boolean) }

    private fun setCurrentDetailBulletinId(id: Long) =
        updateState { copy(currentDetailBulletinId = id) }

    private fun setCurrentDetailBulletin(entity: BulletinEntity) =
        updateState { copy(currentDetailBulletin = entity) }

    //---

    override fun onCommentWritingInput(input: String) {
        if (input.length <= 255) updateState { copy(commentWritingInput = input) }
    }

    override fun showReportBottomSheet() {
        updateState {
            copy(
                isShowDreamBottomSheetWithTextButtons = true,
                bottomSheetItems = listOf(
                    "유출/사칭/사기",
                    "욕설/비하",
                    "불법촬영물 등의 유통",
                    "정당/정치인 비하 및 선거운동",
                    "음란물/불건전한 만남 및 대화",
                    "낚시/놀람/도배",
                    "상업적 광고 및 판매"
                ).map {
                    TextAndOnClick(
                        it,
                    ) {
                        showDialog(
                            header = "",
                            description = "\"$it\"으로 신고하시겠습니까?",
                            onConfirm = { showSimpleDialog("신고되었습니다") },
                            onDismiss = { setIsShowDialog(false) },
                        )
                    }
                },
            )
        }
    }

    override fun showSimpleDialog(text: String) {
        updateState {
            copy(
                isShowSimpleDialog = true,
                simpleDialogText = text,
            )
        }
    }

    override fun showDialog(
        header: String,
        description: String,
        onConfirm: () -> Unit,
        onDismiss: () -> Unit,
    ) {
        updateState {
            copy(
                dialogHeader = header,
                dialogDescription = description,
                dialogOnConfirm = onConfirm,
                dialogOnDismiss = onDismiss,
                isShowDialog = true,
            )
        }
    }

    override fun showBottomSheet(bottomSheetItems: List<TextAndOnClick>) {
        updateState {
            copy(
                isShowDreamBottomSheetWithTextButtons = true,
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
            updateState { copy(commentWritingInput = "") }

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
                    updateState {
                        copy(
                            isLoadDetailSuccessful = false,
                            isInitialLoadingFinished = true,
                        )
                    }
                    Timber.d("loadBulletin 코루틴 실패, id: $id")
                } else {
                    updateState {
                        copy(
                            isLoadDetailSuccessful = true,
                            isInitialLoadingFinished = true,
                        )
                    }
                    setCurrentDetailBulletin(entity)
                    Timber.d("loadBulletin 코루틴 성공, id: $id")
                }
            } catch (e: Throwable) {
                Timber.e(e, "loadBulletin 코루틴 에러, id: $id")
                updateState {
                    copy(
                        isLoadDetailSuccessful = false,
                        isInitialLoadingFinished = true,
                    )
                }
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

    override fun bookmarkBulletin() {
        loadingScope {
            val changedBookmark =
                communityRepository.bookmarkBulletin(state.value.currentDetailBulletinId)

            // 귀찮다 그냥 로드하자
            loadBulletin(state.value.currentDetailBulletinId)
        }
    }

    init {
        loadBulletin(id)
    }

}
