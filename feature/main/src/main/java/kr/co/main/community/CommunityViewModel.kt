package kr.co.main.community

import android.net.Uri
import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.co.domain.entity.BulletinEntity
import kr.co.domain.entity.CropEntity
import kr.co.domain.repository.CommunityRepository
import kr.co.domain.repository.ServerImageRepository
import kr.co.main.community.temp.WritingSelectedImageModel
import kr.co.ui.base.BaseViewModel
import timber.log.Timber
import java.io.File
import javax.inject.Inject

internal interface CommunityScreenEvent {
    fun onSearchInputChanged(input: String)
    fun setIsShowWaitingDialog(boolean: Boolean)
    fun onBulletinWritingInputChanged(input: String)
    fun onCommentWritingInput(input: String)
    fun setBulletinEntities(entities: List<BulletinEntity>)
    fun setCurrentDetailBulletinId(id: Long)
    fun setCurrentDetailBulletin(entity: BulletinEntity)
}

@HiltViewModel
internal class CommunityViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val serverImageRepository: ServerImageRepository,
    private val communityRepository: CommunityRepository,
) : BaseViewModel<CommunityViewModel.State>(savedStateHandle), CommunityScreenEvent {

    override fun createInitialState(savedState: Parcelable?): State {
        return State()
    }

    data class State(
        // TODO: currentBoard 초기값? 혹은 들어올 때 받아서.
        val currentBoard: CropEntity.Name = CropEntity.Name.PEPPER,
        // TODO: currentCategory 초기값? 혹은 들어올 때 받아서.
        val currentCategory: BulletinEntity.BulletinCategory = BulletinEntity.BulletinCategory.Free,
        val searchInput: String = "",
        val isShowWaitingDialog: Boolean = false,
        val bulletinWritingInput: String = "",
        val commentWritingInput: String = "",
        val writingImages: List<WritingSelectedImageModel> = emptyList(),
        val bulletinEntities: List<BulletinEntity> = emptyList(),
        val currentDetailBulletinId: Long = 0L,
        val currentDetailBulletin: BulletinEntity = BulletinEntity.dummy(),
        val isLoadDetailSuccessful: Boolean = false,
//        val aa = 22,
//        val aa = 22,
    ) : BaseViewModel.State

    override fun onSearchInputChanged(input: String) {
        updateState { copy(searchInput = input) }
    }

    override fun setIsShowWaitingDialog(boolean: Boolean) {
        updateState { copy(isShowWaitingDialog = boolean) }
    }

    override fun onBulletinWritingInputChanged(input: String) {
        updateState { copy(bulletinWritingInput = input) }
    }

    override fun onCommentWritingInput(input: String) {
        updateState { copy(commentWritingInput = input) }
    }

    override fun setBulletinEntities(entities: List<BulletinEntity>) {
        updateState { copy(bulletinEntities = entities) }
    }

    override fun setCurrentDetailBulletinId(id: Long) {
        updateState { copy(currentDetailBulletinId = id) }
    }

    override fun setCurrentDetailBulletin(entity: BulletinEntity) {
        updateState { copy(currentDetailBulletin = entity) }
    }

    fun onBulletinClick(id: Long) {
        setCurrentDetailBulletinId(id)
        loadBulletin(id)
    }


    private fun addWritingImage(model: WritingSelectedImageModel) {
        updateState { copy(writingImages = writingImages + model) }
    }

    private fun removeWritingImage(model: WritingSelectedImageModel) {
        updateState { copy(writingImages = writingImages - model) }
    }

    private fun replaceWritingImagesByUri(model: WritingSelectedImageModel) {
        val idx = state.value.writingImages.indexOfFirst { it.uri == model.uri }
        if (idx < 0) return
        updateState {
            copy(writingImages = writingImages.toMutableList().apply {
                removeAt(idx)
                add(idx, model)
            })
        }
    }

    fun onAddImagesClick(uris: List<Uri>, uriToFile: (Uri) -> File) {
        for (uri in uris) {
            val model = WritingSelectedImageModel(uri = uri)
            addWritingImage(model)
            viewModelScope.launch {
                Timber.d("uploadImage 코루틴 시작")
                try {
                    val serverImageEntity = uploadImage(uriToFile(uri))
                    serverImageEntity?.let { replaceWritingImagesByUri(model.copy(url = it.url)) }
                    Timber.d("uploadImage 코루틴 끝, url: $serverImageEntity")
                } catch (e: Throwable) {
                    Timber.e(e, "uploadImage 코루틴 에러")
                }
            }
        }
        Timber.d("addImages 끝")
    }

    fun onRemoveImageClick(model: WritingSelectedImageModel) {
        viewModelScope.launch {
            try {
                val boolean = serverImageRepository.delete(model.url.toString())
                Timber.d("onRemoveImageClick 코루틴 끝, boolean: $boolean")
            } catch (e: Throwable) {
                Timber.e(e, "onRemoveImageClick 코루틴 에러")
            }
        }

        removeWritingImage(model)

//        val index = writingImages.value.indexOfFirst { it == model }
//        if (index >= 0) _writingImages.value =
//            writingImages.value.toMutableList().apply { removeAt(index) }
    }

    private suspend fun uploadImage(file: File) = serverImageRepository.upload("bulletin", file)

    fun onFinishWritingClick(popBackStack: () -> Unit) {
        Timber.d("onFinishWritingClick 시작")
        viewModelScope.launch {
            try {
                val uploadedBulletinId = communityRepository.postBulletin(
                    content = state.value.bulletinWritingInput,
                    crop = state.value.currentBoard,
                    bulletinCategory = state.value.currentCategory,
//                    imageUrls = writingImages.value.map { it.url.toString() },
                    imageUrls = emptyList(),  // temp
                )
                Timber.d("onFinishWritingClick 코루틴 끝, id: $uploadedBulletinId")
                popBackStack()
            } catch (e: Throwable) {
                Timber.e(e, "onFinishWritingClick 코루틴 에러")
            }
        }
        Timber.d("onFinishWritingClick 끝")
    }

    fun onFreeCategoryClick() {
        viewModelScope.launch {
            try {
                val bulletins = communityRepository.getBulletins(
                    keyword = null,
                    bulletinCategory = BulletinEntity.BulletinCategory.Free,
                    crop = state.value.currentBoard,
                    lastBulletinId = null,
                )
                setBulletinEntities(bulletins)
            } catch (e: Throwable) {
                Timber.e(e, "onFreeCategoryClick 코루틴 에러")
            }
        }
    }

    private fun loadBulletin(id: Long) {
        viewModelScope.launch {
            Timber.d("loadBulletin 코루틴 시작, id: $id")
            Timber.d("loadBulletin 코루틴 시작, id: ${state.value.currentDetailBulletinId}")
            Timber.d("loadBulletin 코루틴 시작, id: ${state.value.currentDetailBulletinId}")
            try {
                val entity = communityRepository.getBulletinDetail(id)
                if (entity == null) {
                    updateState { copy(isLoadDetailSuccessful = false) }
                } else {
                    updateState { copy(isLoadDetailSuccessful = true) }
                    setCurrentDetailBulletin(entity)
                }
            } catch (e: Throwable) {
                Timber.e(e, "loadBulletin 코루틴 에러, id: $id")
                updateState { copy(isLoadDetailSuccessful = false) }
            }
            Timber.d("loadBulletin 코루틴 끝, id: $id")
        }
    }

    init {
        updateState { copy(bulletinEntities = List(10) { i -> BulletinEntity.dummy(i) }) }
    }

}
