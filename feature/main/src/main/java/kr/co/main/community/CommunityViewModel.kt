package kr.co.main.community

import android.os.Parcelable
import androidx.compose.foundation.lazy.LazyListState
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
    fun dismissBottomSheet()
    fun showBottomSheet(bottomSheetItems: List<TextAndOnClick>)
    fun onSelectBoard(crop: CropType)
    fun onSearchRun()

    /** CommunityDialogSimpleTitle */
    fun showSimpleDialog(text: String)
    fun dismissSimpleDialog()

    companion object {
        val dummy = object : CommunityScreenEvent {
            override fun onSearchInputChanged(input: String) {}
            override fun setBulletinEntities(entities: List<BulletinEntity>) {}
            override fun bookmarkBulletin(id: Long) {}
            override fun onCategoryClick(category: BulletinEntity.BulletinCategory) {}
            override fun dismissBottomSheet() {}
            override fun showBottomSheet(bottomSheetItems: List<TextAndOnClick>) {}
            override fun onSelectBoard(crop: CropType) {}
            override fun onSearchRun() {}
            override fun showSimpleDialog(text: String) {}
            override fun dismissSimpleDialog() {}

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
        val lazyListState: LazyListState = LazyListState(),

        // simple dialog
        val isShowSimpleDialog: Boolean = false,
        val simpleDialogText: String = "",
    ) : BaseViewModel.State

    override fun dismissBottomSheet() = updateState { copy(isShowBottomSheet = false) }
    override fun dismissSimpleDialog() = updateState { copy(isShowSimpleDialog = false) }

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

    private fun loadBulletins(
        crop: CropType,
        category: BulletinEntity.BulletinCategory,
        keyword: String? = null,
        lastBulletinId: Long? = null,
        onErrorCallback: (() -> Unit)? = null,
        callback: (() -> Unit)? = null,
    ) {
        viewModelScope.launch {
            try {
                Timber.d("loadBulletins 코루틴 시작, crop: $crop, category: $category, keyword: $keyword")
                val bulletins = communityRepository.getBulletins(
                    keyword = keyword,
                    bulletinCategory = category,
                    crop = crop,
                    lastBulletinId = lastBulletinId,
                )
                Timber.d("loadBulletins 코루틴 성공, $bulletins")
                updateState {
                    copy(
                        currentBoard = crop,
                        currentCategory = category,
                        searchInput = keyword ?: "",
                        bulletinEntities = bulletins,
                    )
                }
                state.value.lazyListState.scrollToItem(0)
                callback?.invoke()
            } catch (e: Throwable) {
                Timber.e(e, "loadBulletins error")
                showSimpleDialog("게시글을 불러오지 못했습니다")
                onErrorCallback?.invoke()
            }
        }
    }

    private fun loadBulletinsWithDisable(
        crop: CropType,
        category: BulletinEntity.BulletinCategory,
        keyword: String? = null,
        lastBulletinId: Long? = null,
        onErrorCallback: (() -> Unit)? = null,
        callback: (() -> Unit)? = null,
    ) {
        updateState { copy(isEnable = false) }
        loadBulletins(
            crop = crop,
            category = category,
            keyword = keyword,
            lastBulletinId = lastBulletinId,
            onErrorCallback = {
                updateState { copy(isEnable = true) }
                onErrorCallback?.invoke()
            },
        ) {
            updateState { copy(isEnable = true) }
            callback?.invoke()
        }
    }

    override fun onSearchInputChanged(input: String) {
        if (input.length <= 255 && '\n' !in input)
            updateState { copy(searchInput = input) }
    }

    override fun onSearchRun() {
        loadBulletinsWithDisable(
            crop = state.value.currentBoard,
            category = state.value.currentCategory,
            keyword = state.value.searchInput.let { it.ifBlank { null } },
        )
    }

    override fun onSelectBoard(crop: CropType) {
        loadBulletinsWithDisable(
            crop = crop,
            category = state.value.currentCategory,
        )
    }

    override fun showSimpleDialog(text: String) {
        updateState {
            copy(
                isShowSimpleDialog = true,
                simpleDialogText = text,
            )
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
        loadBulletins(
            crop = state.value.currentBoard,
            category = category,
        )
    }

    // TODO:
    override fun bookmarkBulletin(id: Long) {
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
