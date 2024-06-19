package kr.co.main.community

import android.net.Uri
import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

@HiltViewModel
internal class CommunityViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val serverImageRepository: ServerImageRepository,
    private val communityRepository: CommunityRepository,
) : BaseViewModel<CommunityViewModel.State>(savedStateHandle) {

    // TODO: currentBoard 초기값? 혹은 들어올 때 받아서.
    private val _currentBoard = MutableStateFlow(CropEntity.Name.PEPPER)
    val currentBoard = _currentBoard.asStateFlow()

    // TODO: currentCategory 초기값? 혹은 들어올 때 받아서.
    private val _currentCategory = MutableStateFlow(BulletinEntity.BulletinCategory.Free)
    val currentCategory = _currentCategory.asStateFlow()

    private val _searchInput = MutableStateFlow("")
    val searchInput = _searchInput.asStateFlow()
    fun onSearchInputChanged(input: String) {
        _searchInput.value = input
    }

    private val _isShowWaitingDialog = MutableStateFlow(false)
    val isShowWaitingDialog = _isShowWaitingDialog.asStateFlow()
    fun setIsShowWaitingDialog(boolean: Boolean) {
        _isShowWaitingDialog.value = boolean
    }

    private val _bulletinWritingInput = MutableStateFlow("")
    val bulletinWritingInput = _bulletinWritingInput.asStateFlow()
    fun onBulletinWritingInputChanged(input: String) {
        _bulletinWritingInput.value = input
    }

    private val _commentWritingInput = MutableStateFlow("")
    val commentWritingInput = _commentWritingInput.asStateFlow()
    fun onCommentWritingInput(input: String) {
        _commentWritingInput.value = input
    }

    private val _writingImages = MutableStateFlow(listOf<WritingSelectedImageModel>())
    val writingImages = _writingImages.asStateFlow()
    fun onAddImagesClick(images: List<Uri>, uriToFile: (Uri) -> File) {
        _writingImages.value =
            writingImages.value + images.map { WritingSelectedImageModel(uri = it) }

        // TODO: 테스트용으로 첫번째 이미지 업로드
        if (images.isNotEmpty()) {
            val image = images[0]
            viewModelScope.launch {
                Timber.d("uploadImage 코루틴 시작")
                try {
                    val url = uploadImage(uriToFile(image))
                    Timber.d("uploadImage 코루틴 끝, url: $url")
                } catch (e: Throwable) {
                    Timber.e(e, "uploadImage 코루틴 에러")
                }
            }

//            viewModelScopeEH.launch {
//                val url = uploadImage(context, "bulletin", image)
//                Timber.d("uploadImage 코루틴 끝, url: $url")
//            }
        }

        Timber.d("addImages 끝")
    }

    fun onRemoveImageClick(image: Uri) {
        val index = writingImages.value.indexOfFirst { it.uri == image }
        if (index < 0) return
        _writingImages.value = writingImages.value.toMutableList().apply { removeAt(index) }
    }

    private suspend fun uploadImage(file: File) = serverImageRepository.upload("bulletin", file)

    fun onFinishWritingClick() {
        Timber.d("onFinishWritingClick 시작")
        viewModelScope.launch {
            try {
                val uploadedBulletinId = communityRepository.postBulletin(
                    content = bulletinWritingInput.value,
                    dreamCrop = currentBoard.value.name,
                    bulletinCategory = currentCategory.value.name,
//                    imageUrls = writingImages.value.map { it.url.toString() },
                    imageUrls = emptyList(),  // temp
                )
                Timber.d("onFinishWritingClick 코루틴 끝, id: $uploadedBulletinId")
            } catch (e: Throwable) {
                Timber.e(e, "onFinishWritingClick 코루틴 에러")
            }
        }
        Timber.d("onFinishWritingClick 끝")
    }

    private val _bulletinEntities = MutableStateFlow(listOf<BulletinEntity>())
    val bulletinEntities = _bulletinEntities.asStateFlow()
    private fun setBulletinEntities(entities: List<BulletinEntity>) {
        _bulletinEntities.value = entities
    }

    fun onFreeCategoryClick() {
        viewModelScope.launch {
            try {
                val bulletins = communityRepository.getBulletins(
                    keyword = null,
                    bulletinCategory = BulletinEntity.BulletinCategory.Free,
                    crop = currentBoard.value,
                    lastBulletinId = null,
                )
                setBulletinEntities(bulletins)
            } catch (e: Throwable) {
                Timber.e(e, "onFreeCategoryClick 코루틴 에러")
            }
        }
    }

    init {
        _bulletinEntities.value = List(10) { i -> BulletinEntity.dummy(i) }
    }

    override fun createInitialState(savedState: Parcelable?): State {
        return State()
    }

    data class State(
        val state: Any? = null
    ) : BaseViewModel.State
}
