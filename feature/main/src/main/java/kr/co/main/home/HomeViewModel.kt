package kr.co.main.home

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<HomeViewModel.State>(savedStateHandle) {


    init {
    }

    data class State(
        val address: String? = null,
        val todayWeather: WeatherDetail = WeatherDetail(),
        val weatherList: List<WeatherSimple>? = null,
    ): BaseViewModel.State {
        data class WeatherDetail(
            val probability: Int = 0,
            val precipitation: Int = 0,
            val humidity: Int = 0,
            val wind: Int = 0,
            val temperature: Float = 0f,
        )

        data class WeatherSimple(
            val weather: String? = null,
            val minTemperature: Float? = null,
            val maxTemperature: Float? = null,
            val day: String? = null,
        )
    }

    override fun createInitialState(savedState: Parcelable?) = State()
}
