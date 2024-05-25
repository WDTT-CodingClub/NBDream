package kr.co.wdtt.nbdream.ui.home

import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kr.co.wdtt.core.ui.base.BaseViewModel
import kr.co.wdtt.core.ui.base.CustomErrorType
import kr.co.wdtt.nbdream.data.mapper.EntityWrapper
import kr.co.wdtt.nbdream.domain.usecase.IGetDayWeatherForecast
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getDayWeatherForecast: IGetDayWeatherForecast,
) : BaseViewModel() {
    private val _state = MutableStateFlow(listOf(""))
    val state = _state.asStateFlow()
    override suspend fun onError(errorType: CustomErrorType) {
        TODO("Not yet implemented")
    }

    init {
        viewModelScopeEH.launch {
            getDayWeatherForecast(
                "20240525",
                "0500",
                "55",
                "127"
            ).onEach {
                when (it) {
                    is EntityWrapper.Success -> {
                        _state.value += it.data.last().weather
                    }

                    is EntityWrapper.Fail -> {}
                }
            }.collect {
                Log.d("test", it.toString())
            }
        }
    }

}
