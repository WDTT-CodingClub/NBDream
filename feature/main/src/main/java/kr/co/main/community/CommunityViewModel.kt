package kr.co.main.community

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.co.domain.entity.BulletinEntity
import kr.co.domain.entity.type.CropType
import kr.co.domain.repository.CommunityRepository
import kr.co.ui.base.BaseViewModel
import kr.co.ui.widget.TextAndOnClick
import timber.log.Timber
import javax.inject.Inject

internal interface CommunityScreenEvent {
    fun onSearchInputChanged(input: String)
    fun setBulletinEntities(entities: List<BulletinEntity>)
    fun bookmarkBulletin(id: Long)
    fun onCategoryClick(category: BulletinEntity.BulletinCategory)
    fun setIsShowBottomSheet(boolean: Boolean)
    fun showBottomSheet(bottomSheetItems: List<TextAndOnClick>)
    fun onSelectBoard(crop: CropType)
    fun onSearchRun()

    companion object {
        val dummy = object : CommunityScreenEvent {
            override fun onSearchInputChanged(input: String) {}
            override fun setBulletinEntities(entities: List<BulletinEntity>) {}
            override fun bookmarkBulletin(id: Long) {}
            override fun onCategoryClick(category: BulletinEntity.BulletinCategory) {}
            override fun setIsShowBottomSheet(boolean: Boolean) {}
            override fun showBottomSheet(bottomSheetItems: List<TextAndOnClick>) {}
            override fun onSelectBoard(crop: CropType) {}
            override fun onSearchRun() {}

        }
    }
}

@HiltViewModel
internal class CommunityViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val communityRepository: CommunityRepository,
) : BaseViewModel<CommunityViewModel.State>(savedStateHandle), CommunityScreenEvent {

    override fun createInitialState(savedState: Parcelable?) = State()

    data class State(
        val currentBoard: CropType = CropType.PEPPER,
        val currentCategory: BulletinEntity.BulletinCategory = BulletinEntity.BulletinCategory.Free,
        val searchInput: String = "",
        val bulletinWritingInput: String = "",
        val bulletinEntities: List<BulletinEntity> = emptyList(),
        val isLoadDetailSuccessful: Boolean = false,
        val isEnable: Boolean = true,
        val isShowBottomSheet: Boolean = false,
        val bottomSheetItems: List<TextAndOnClick> = emptyList(),
    ) : BaseViewModel.State

    override fun setIsShowBottomSheet(boolean: Boolean) =
        updateState { copy(isShowBottomSheet = boolean) }

    override fun onSearchInputChanged(input: String) = updateState { copy(searchInput = input) }

    override fun setBulletinEntities(entities: List<BulletinEntity>) =
        updateState { copy(bulletinEntities = entities) }

    private fun setCurrentCategory(category: BulletinEntity.BulletinCategory) =
        updateState { copy(currentCategory = category) }

    // TODO: 게시판에서 북마크 처리용 bulletinEntities 게시글 하나 갱신하는거
    fun aa() {
        val aa = state.value.bulletinEntities.indexOfFirst { true }
        updateState { copy(bulletinEntities = bulletinEntities) }
    }

    //---

    override fun onSearchRun() {
        Timber.d("onSearchRun 시작, searchInput: ${state.value.searchInput}")
        updateState { copy(isEnable = false) }
        loadingScope {
            val bulletins = communityRepository.getBulletins(
                keyword = state.value.searchInput,
                bulletinCategory = state.value.currentCategory,
                crop = state.value.currentBoard,
                lastBulletinId = null,
            )
            Timber.d("onSearchRun 코루틴 성공, $bulletins")
            updateState {
                copy(
                    isEnable = true,
                    bulletinEntities = bulletins,
                )
            }
        }
    }

    override fun onSelectBoard(crop: CropType) {
        updateState { copy(isEnable = false) }
        loadingScope {
            val bulletins = communityRepository.getBulletins(
                keyword = null,
                bulletinCategory = state.value.currentCategory,
                crop = crop,
                lastBulletinId = null,
            )
            Timber.d("onSelectBoard 코루틴 성공, $bulletins")
            updateState {
                copy(
                    isShowBottomSheet = false,
                    isEnable = true,
                    currentBoard = crop,
                    bulletinEntities = bulletins,
                )
            }
        }
    }

    override fun showBottomSheet(bottomSheetItems: List<TextAndOnClick>) {
        updateState {
            copy(
                isShowBottomSheet = true,
                bottomSheetItems = bottomSheetItems,
            )
        }
    }

    override fun onCategoryClick(category: BulletinEntity.BulletinCategory) {
        viewModelScope.launch {
            try {
                setCurrentCategory(category)
                val bulletins = communityRepository.getBulletins(
                    keyword = null,
                    bulletinCategory = category,
                    crop = state.value.currentBoard,
                    lastBulletinId = null,
                )
                setBulletinEntities(bulletins)
                Timber.d("onFreeCategoryClick 코루틴 성공, $bulletins")
            } catch (e: Throwable) {
                Timber.e(e, "onFreeCategoryClick 코루틴 에러")
            }
        }
    }

    // TODO:
    override fun bookmarkBulletin(id: Long) {
//        loadingScope {
//            val changedBookmark =
//                communityRepository.bookmarkBulletin(id)
//
////            // 그냥 로드하자
////            loadBulletin(state.value.currentDetailBulletinId)
//        }
    }

    init {
        getBulletins()
    }

    fun getBulletins() {
        loadingScope {
            val bulletins = communityRepository.getBulletins(
                keyword = null,
                bulletinCategory = state.value.currentCategory,
                crop = state.value.currentBoard,
                lastBulletinId = null,
            )
            setBulletinEntities(bulletins)
        }
    }
}
