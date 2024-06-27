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
import timber.log.Timber
import javax.inject.Inject

internal interface SharingData {
    val getCurrentBoard: () -> CropType
    val getCurrentCategory: () -> BulletinEntity.BulletinCategory
}

internal interface CommunityScreenEvent {
    fun onSearchInputChanged(input: String)
    fun setBulletinEntities(entities: List<BulletinEntity>)
    fun bookmarkBulletin(id: Long)
    fun onCategoryClick(category: BulletinEntity.BulletinCategory)

    companion object {
        val dummy = object : CommunityScreenEvent {
            override fun onSearchInputChanged(input: String) {}
            override fun setBulletinEntities(entities: List<BulletinEntity>) {}
            override fun bookmarkBulletin(id: Long) {}
            override fun onCategoryClick(category: BulletinEntity.BulletinCategory) {}

        }
    }
}

@HiltViewModel
internal class CommunityViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val communityRepository: CommunityRepository,
) : BaseViewModel<CommunityViewModel.State>(savedStateHandle), CommunityScreenEvent, SharingData {

    override fun createInitialState(savedState: Parcelable?) = State()

    data class State(
        val currentBoard: CropType = CropType.PEPPER,
        val currentCategory: BulletinEntity.BulletinCategory = BulletinEntity.BulletinCategory.Free,
        val searchInput: String = "",
        val bulletinWritingInput: String = "",
        val bulletinEntities: List<BulletinEntity> = emptyList(),
        val isLoadDetailSuccessful: Boolean = false,
    ) : BaseViewModel.State

    override val getCurrentBoard: () -> CropType
        get() = { state.value.currentBoard }
    override val getCurrentCategory: () -> BulletinEntity.BulletinCategory
        get() = { state.value.currentCategory }

    override fun onSearchInputChanged(input: String) = updateState { copy(searchInput = input) }

    override fun setBulletinEntities(entities: List<BulletinEntity>) =
        updateState { copy(bulletinEntities = entities) }

    private fun setCurrentCategory(category: BulletinEntity.BulletinCategory) =
        updateState { copy(currentCategory = category) }

    // TODO: bulletinEntities 게시글 하나 갱신하는거
    fun aa() {
        val aa = state.value.bulletinEntities.indexOfFirst { true }
        updateState { copy(bulletinEntities = bulletinEntities) }
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

    override fun bookmarkBulletin(id: Long) {
//        // TODO: -ing : postCard 얹고 다시.
//        loadingScope {
//            val changedBookmark =
//                communityRepository.bookmarkBulletin(id)
//
////            // 귀찮다 그냥 로드하자
////            loadBulletin(state.value.currentDetailBulletinId)
//        }
    }

    init {
        updateState { copy(bulletinEntities = List(10) { i -> BulletinEntity.dummy(i) }) }
    }

}
