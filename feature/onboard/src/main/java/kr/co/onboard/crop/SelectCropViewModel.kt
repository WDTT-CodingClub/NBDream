package kr.co.onboard.crop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kr.co.domain.entity.CropEntity
import kr.co.domain.entity.type.CropType
import kr.co.nbdream.core.ui.R
import kr.co.onboard.crop.model.CropItem
import javax.inject.Inject

@HiltViewModel
class SelectCropViewModel @Inject constructor(): ViewModel() {
     private val _crops = MutableStateFlow<List<CropItem>>(emptyList())
    val crops: StateFlow<List<CropItem>> = _crops

    init {
        loadCrops()
    }

    private fun loadCrops() {
        viewModelScope.launch {
            _crops.value = listOf(
                CropItem(CropType.PEPPER, R.drawable.img_logo),
                CropItem(CropType.RICE, R.drawable.img_logo),
                CropItem(CropType.POTATO, R.drawable.img_logo),
                CropItem(CropType.SWEET_POTATO, R.drawable.img_logo),
                CropItem(CropType.APPLE, R.drawable.img_logo),
                CropItem(CropType.STRAWBERRY, R.drawable.img_logo),
                CropItem(CropType.GARLIC, R.drawable.img_logo),
                CropItem(CropType.LETTUCE, R.drawable.img_logo),
                CropItem(CropType.NAPPA_CABBAGE, R.drawable.img_logo),
                CropItem(CropType.TOMATO, R.drawable.img_logo)
            )
        }
    }
}

