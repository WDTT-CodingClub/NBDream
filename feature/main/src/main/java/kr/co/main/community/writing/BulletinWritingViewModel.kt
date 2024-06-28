package kr.co.main.community.writing

import android.net.Uri
import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kr.co.common.util.FileUtil
import kr.co.domain.entity.BulletinEntity
import kr.co.domain.entity.type.CropType
import kr.co.domain.repository.CommunityRepository
import kr.co.domain.repository.ServerImageRepository
import kr.co.domain.usecase.image.UploadImageUseCase
import kr.co.main.community.temp.WritingSelectedImageModel
import kr.co.ui.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
internal class BulletinWritingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val serverImageRepository: ServerImageRepository,
    private val communityRepository: CommunityRepository,
    private val uploadImageUseCase: UploadImageUseCase,
) : BaseViewModel<BulletinWritingViewModel.State>(savedStateHandle) {
    private val id: Long? = savedStateHandle.get<String>("id")?.toLongOrNull()
    private val _complete: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val complete = _complete.asSharedFlow()
    override fun createInitialState(savedState: Parcelable?) = State()

    private val savedStateHandleCrop =
        savedStateHandle.get<String>("crop").also { Timber.d("savedStateHandleCrop: $it") }
            ?.toIntOrNull()
            ?.let { if (it in CropType.entries.indices) CropType.entries[it] else null }
            ?: CropType.PEPPER
    private val savedStateHandleCategory =
        savedStateHandle.get<String>("category").also { Timber.d("savedStateHandleCategory: $it") }
            ?.toIntOrNull()
            ?.let { if (it in BulletinEntity.BulletinCategory.entries.indices) BulletinEntity.BulletinCategory.entries[it] else null }
            ?: BulletinEntity.BulletinCategory.Free

    init {
        id?.let {
            fetchBulletin(it)
        } ?: run {
            updateState {
                copy(
                    currentBoard = savedStateHandleCrop,
                    currentCategory = savedStateHandleCategory,
                )
            }
        }
    }

    data class State(
        val id: Long? = null,
        val writingImages: List<WritingSelectedImageModel> = emptyList(),
        val bulletinWritingInput: String = "",
        val currentBoard: CropType = CropType.PEPPER,
        val currentCategory: BulletinEntity.BulletinCategory = BulletinEntity.BulletinCategory.Free,
        val isShowWaitingDialog: Boolean = false,
    ) : BaseViewModel.State

    private fun setCurrentCategory(category: BulletinEntity.BulletinCategory) {
        updateState { copy(currentCategory = category) }
    }

    fun setIsShowWaitingDialog(boolean: Boolean) {
        updateState { copy(isShowWaitingDialog = boolean) }
    }

    fun onCategoryClick(category: BulletinEntity.BulletinCategory) {
        setCurrentCategory(category)
    }

    fun onAddImagesClick(uris: List<Uri>) {
        for (uri in uris) {
            val model = WritingSelectedImageModel(uri = uri)
            addWritingImage(model)
            viewModelScope.launch {
                Timber.d("onAddImagesClick 코루틴 시작")
                try {
                    val url = uploadImage(uri)
                    replaceWritingImagesByUri(model.copy(url = url))
                    Timber.d("onAddImagesClick 코루틴 끝, url: $url")
                } catch (e: Throwable) {
                    Timber.e(e, "onAddImagesClick 코루틴 에러")
                }
            }
        }
    }

    private fun addWritingImage(model: WritingSelectedImageModel) {
        updateState { copy(writingImages = writingImages + model) }
    }

    private suspend fun uploadImage(uri: Uri) = uploadImageUseCase(
        UploadImageUseCase.Params(
            domain = UploadImageUseCase.DOMAIN_BULLETIN,
            file = FileUtil.getFileFromUri(uri)
                ?: throw IllegalArgumentException("image file is null")
        )
    )

//    private suspend fun uploadImage9(uri: Uri) =
//        uploadImageUseCase(UploadImageUseCase.DOMAIN_BULLETIN, uri)

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

    fun onBulletinWritingInputChanged(input: String) {
        if (input.length <= 3000) updateState { copy(bulletinWritingInput = input) }
    }

    fun onRemoveImageClick(model: WritingSelectedImageModel) {
        viewModelScope.launch {
            try {
                model.url?.let { serverImageRepository.delete(it) }
                Timber.d("onRemoveImageClick 코루틴 끝")
            } catch (e: Throwable) {
                Timber.e(e, "onRemoveImageClick 코루틴 에러")
            }
        }

        removeWritingImage(model)

//        val index = writingImages.value.indexOfFirst { it == model }
//        if (index >= 0) _writingImages.value =
//            writingImages.value.toMutableList().apply { removeAt(index) }
    }

    private fun removeWritingImage(model: WritingSelectedImageModel) {
        updateState { copy(writingImages = writingImages - model) }
    }

    private fun onFinishWritingClick() {
        loadingScope {
            communityRepository.postBulletin(
                content = state.value.bulletinWritingInput,
                crop = state.value.currentBoard,
                bulletinCategory = state.value.currentCategory,
                imageUrls = state.value.writingImages.map { it.url.toString() },
            )
        }.invokeOnCompletion {
            viewModelScopeEH.launch {
                _complete.emit(it == null)
            }
        }
    }


    private fun fetchBulletin(id: Long) = loadingScope {
        val entity = communityRepository.getBulletinDetail(id)
        val writingImagesList = entity?.imageUrls?.map { url ->
            WritingSelectedImageModel(uri = Uri.parse(url), url = url)
        }
        updateState {
            copy(
                id = id,
                bulletinWritingInput = entity?.content ?: "",
                writingImages = writingImagesList ?: emptyList(),
                currentBoard = entity?.crop?.type ?: CropType.PEPPER,
                currentCategory = entity?.bulletinCategory ?: BulletinEntity.BulletinCategory.Free,
            )
        }
    }

    private fun updateBulletin() = loadingScope {
        id?.let { id ->
            communityRepository.putBulletin(
                id = id,
                content = currentState.bulletinWritingInput,
                crop = currentState.currentBoard,
                bulletinCategory = currentState.currentCategory,
                imageUrls = currentState.writingImages.map { it.url.toString() },
            )
        }
    }.invokeOnCompletion {
        viewModelScopeEH.launch {
            _complete.emit(it == null)
        }
    }

    fun onPerformBulletin() = id?.let {
        updateBulletin()
    } ?: run {
        onFinishWritingClick()
    }

}
