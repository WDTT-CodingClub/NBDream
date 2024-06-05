package kr.co.main.community

import android.icu.text.DecimalFormat
import android.net.Uri
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kr.co.domain.entity.BulletinEntity
import kr.co.domain.entity.plzLookThisPakage.DreamCrop
import kr.co.main.community.temp.WritingSelectedImageModel
import kr.co.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class CommunityViewModel @Inject constructor(
//    private val getDayWeatherForecast: GetDayWeatherForecast,
) : BaseViewModel() {
    private val _currentBoard = MutableStateFlow("")
    val currentBoard = _currentBoard.asStateFlow()

    private val _currentCategory = MutableStateFlow("")
    val currentCategory = _currentCategory.asStateFlow()

    private val _searchInput = MutableStateFlow("")
    val searchInput = _searchInput.asStateFlow()
    fun onSearchInputChanged(input: String) {
        _searchInput.value = input
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
    fun addImages(images: List<Uri>) {
        _writingImages.value =
            writingImages.value + images.map { WritingSelectedImageModel(uri = it) }
    }

    fun removeImage(image: Uri) {
        val index = writingImages.value.indexOfFirst { it.uri == image }
        if (index < 0) return
        _writingImages.value = writingImages.value.toMutableList().apply { removeAt(index) }
    }

    private val _bulletinEntities = MutableStateFlow(listOf<BulletinEntity>())
    val bulletinEntities = _bulletinEntities.asStateFlow()

    init {
        _bulletinEntities.value = List(10) { i ->
            BulletinEntity(
                id = "bulletinId$i",  // 게시글 id가 필요할지 안할지? 필요하다면 어떤 식으로 만들지?
                userId = "userId$i",
                content = "게시글 내용 $i",
                dreamCrop = DreamCrop.PEPPER,
                createdTime = "2000.00.00 00:00:${DecimalFormat("00").format(i)}",
            )
        }
    }

}
