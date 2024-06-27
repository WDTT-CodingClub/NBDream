package kr.co.main.community.writing

import android.net.Uri
import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.co.domain.entity.BulletinEntity
import kr.co.domain.entity.type.CropType
import kr.co.domain.repository.CommunityRepository
import kr.co.domain.repository.ServerImageRepository
import kr.co.main.community.temp.WritingSelectedImageModel
import kr.co.ui.base.BaseViewModel
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@HiltViewModel
internal class BulletinWritingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val serverImageRepository: ServerImageRepository,
    private val communityRepository: CommunityRepository,
) : BaseViewModel<BulletinWritingViewModel.State>(savedStateHandle) {

    override fun createInitialState(savedState: Parcelable?) = State()

    private val savedStateHandleCrop =
        savedStateHandle.get<Int>("crop").also { Timber.d("savedStateHandleCrop: $it") }
            ?.let { CropType.entries[it] } ?: throw IllegalArgumentException("CropType is null")
    private val savedStateHandleCategory =
        savedStateHandle.get<Int>("category").also { Timber.d("savedStateHandleCategory: $it") }
            ?.let { BulletinEntity.BulletinCategory.entries[it] }
            ?: throw IllegalArgumentException("BulletinCategory is null")

    init {
        updateState {
            copy(
                currentBoard = savedStateHandleCrop,
                currentCategory = savedStateHandleCategory,
            )
        }
    }

    data class State(
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

    fun onAddImagesClick(uris: List<Uri>, uriToFile: (Uri) -> File) {
        for (uri in uris) {
            val model = WritingSelectedImageModel(uri = uri)
            addWritingImage(model)
            viewModelScope.launch {
                Timber.d("onAddImagesClick 코루틴 시작")
                try {
                    val serverImageEntity = uploadImage(uriToFile(uri))
                    serverImageEntity?.let { replaceWritingImagesByUri(model.copy(url = it.url)) }
                    Timber.d("onAddImagesClick 코루틴 끝, url: $serverImageEntity")
                } catch (e: Throwable) {
                    Timber.e(e, "onAddImagesClick 코루틴 에러")
                }
            }
        }
    }

    private fun addWritingImage(model: WritingSelectedImageModel) {
        updateState { copy(writingImages = writingImages + model) }
    }

    private suspend fun uploadImage(file: File) = serverImageRepository.upload("bulletin", file)

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
        updateState { copy(bulletinWritingInput = input) }
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

    private fun removeWritingImage(model: WritingSelectedImageModel) {
        updateState { copy(writingImages = writingImages - model) }
    }

    fun onFinishWritingClick(popBackStack: () -> Unit) {
        Timber.d("onFinishWritingClick 시작")
        viewModelScope.launch {
            try {
                val uploadedBulletinId = communityRepository.postBulletin(
                    content = state.value.bulletinWritingInput,
                    crop = state.value.currentBoard,
                    bulletinCategory = state.value.currentCategory,
                    imageUrls = state.value.writingImages.map { it.url.toString() },
//                    imageUrls = emptyList(),  // temp
                )
                Timber.d("onFinishWritingClick 코루틴 끝, id: $uploadedBulletinId")
                popBackStack()
            } catch (e: Throwable) {
                Timber.e(e, "onFinishWritingClick 코루틴 에러")
            }
        }
        Timber.d("onFinishWritingClick 끝")
    }

}
