package kr.co.main.home

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kr.co.domain.usecase.GetDayWeatherForecast
import kr.co.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
) : BaseViewModel() {
    private val _state = MutableStateFlow(listOf(""))
    val state = _state.asStateFlow()


    init {
    }

}
