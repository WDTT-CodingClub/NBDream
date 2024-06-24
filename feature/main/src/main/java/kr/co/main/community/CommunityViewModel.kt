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
import kr.co.domain.entity.type.CropType
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
    private val _currentBoard = MutableStateFlow(CropType.PEPPER)
    private val currentBoard = _currentBoard.asStateFlow()

    // TODO: currentCategory 초기값? 혹은 들어올 때 받아서.
    private val _currentCategory = MutableStateFlow(BulletinEntity.BulletinCategory.Free)
    private val currentCategory = _currentCategory.asStateFlow()

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

//    private fun setWritingImages(list: List<WritingSelectedImageModel>) {
//        _writingImages.value = list
//    }
//
//    private fun addWritingImages(list: List<WritingSelectedImageModel>) {
//        _writingImages.value += list
//    }

    private fun addWritingImage(model: WritingSelectedImageModel) {
        _writingImages.value += model
    }

    private fun removeWritingImage(model: WritingSelectedImageModel) {
        _writingImages.value -= model  // 이거 이렇게 해도 되려나..?
    }

    private fun replaceWritingImagesByUri(model: WritingSelectedImageModel) {
        val idx = writingImages.value.indexOfFirst { it.uri == model.uri }
        if (idx < 0) return
        _writingImages.value = writingImages.value.toMutableList().apply {
            removeAt(idx)
            add(idx, model)
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
                    content = bulletinWritingInput.value,
                    crop = currentBoard.value,
                    bulletinCategory = currentCategory.value,
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

    private val _currentDetailBulletinId = MutableStateFlow(0L)
    val currentDetailBulletinId = _currentDetailBulletinId.asStateFlow()
    private fun setCurrentDetailBulletinId(id: Long) {
        _currentDetailBulletinId.value = id
    }

    private val _currentDetailBulletin = MutableStateFlow(BulletinEntity.dummy())
    val currentDetailBulletin = _currentDetailBulletin.asStateFlow()
    private fun setCurrentDetailBulletin(entity: BulletinEntity) {
        _currentDetailBulletin.value = entity
    }

    private val _isLoadDetailSuccessful = MutableStateFlow(false)
    val isLoadDetailSuccessful = _isLoadDetailSuccessful.asStateFlow()

    fun onBulletinClick(id: Long) {
        setCurrentDetailBulletinId(id)
        loadBulletin(id)
    }

    private fun loadBulletin(id: Long) {
        viewModelScope.launch {
            Timber.d("loadBulletin 코루틴 시작, id: $id")
            Timber.d("loadBulletin 코루틴 시작, id: ${_currentDetailBulletinId.value}")
            Timber.d("loadBulletin 코루틴 시작, id: ${currentDetailBulletinId.value}")
            try {
                val entity = communityRepository.getBulletinDetail(id)
                if (entity == null) {
                    _isLoadDetailSuccessful.emit(false)
                } else {
                    _isLoadDetailSuccessful.emit(true)
                    setCurrentDetailBulletin(entity)
                }
            } catch (e: Throwable) {
                Timber.e(e, "loadBulletin 코루틴 에러, id: $id")
                _isLoadDetailSuccessful.emit(false)
            }
            Timber.d("loadBulletin 코루틴 끝, id: $id")
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
