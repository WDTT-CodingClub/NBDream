package kr.co.wdtt.nbdream.ui.main.home

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kr.co.wdtt.core.ui.base.BaseViewModel
import kr.co.wdtt.core.ui.base.CustomErrorType
import kr.co.wdtt.nbdream.data.mapper.EntityWrapper
import kr.co.wdtt.nbdream.domain.usecase.GetDayWeatherForecast
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getDayWeatherForecast: GetDayWeatherForecast,
) : BaseViewModel() {
    private val _state = MutableStateFlow(listOf(""))
    val state = _state.asStateFlow()


    init {
        viewModelScopeEH.launch {
            getDayWeatherForecast(
                "20240527",
                "0500",
                "55",
                "127"
            ).map {
                if (it is EntityWrapper.Success)
                it.data.map { it.toString() }
                else listOf("")
            }
                .bindState(_state)
        }
    }

}
