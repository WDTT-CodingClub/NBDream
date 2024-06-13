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
        val todayWeather: WeatherDetail? = null,
        val weatherList: List<WeatherSimple>? = null,
    ): BaseViewModel.State {
        data class WeatherDetail(
            val probability: Int? = null,
            val precipitation: Int? = null,
            val humidity: Int? = null,
            val temperature: Float? = null,
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
