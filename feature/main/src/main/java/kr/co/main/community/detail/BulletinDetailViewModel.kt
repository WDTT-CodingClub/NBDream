package kr.co.main.community.detail

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.co.domain.entity.BulletinEntity
import kr.co.domain.repository.CommunityRepository
import kr.co.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class BulletinDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val communityRepository: CommunityRepository,
) : BaseViewModel<BulletinDetailViewModel.State>(savedStateHandle) {

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
    ) : BaseViewModel.State

    fun setIsShowBulletinMoreBottomSheet(boolean: Boolean) =
        updateState { copy(isShowBulletinMoreBottomSheet = boolean) }

    fun setIsShowDeleteCheckDialog(boolean: Boolean) =
        updateState { copy(isShowDeleteCheckDialog = boolean) }

    fun setIsShowFailedDialog(boolean: Boolean) =
        updateState { copy(isShowFailedDialog = boolean) }

    fun onCommentWritingInput(input: String) {
        updateState { copy(commentWritingInput = input) }
    }

    private fun setCurrentDetailBulletinId(id: Long) {
        updateState { copy(currentDetailBulletinId = id) }
    }

    private fun setCurrentDetailBulletin(entity: BulletinEntity) {
        updateState { copy(currentDetailBulletin = entity) }
    }

    init {
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

    fun deleteBulletin(
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

}
