package kr.co.main.home

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kr.co.domain.usecase.user.FetchUserUseCase
import kr.co.domain.usecase.weather.GetDayWeatherForecastUseCase
import kr.co.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val fetchUserUseCase: FetchUserUseCase,
    private val weatherUseCase: GetDayWeatherForecastUseCase,
) : BaseViewModel<HomeViewModel.State>(savedStateHandle) {

    private fun onTodayWeather(weather: State.WeatherDetail) = updateState {
        copy(todayWeather = weather)
    }

    private fun onWeatherList(weatherList: List<State.WeatherSimple>) = updateState {
        copy(weatherList = weatherList)
    }

    init {
        viewModelScopeEH.launch {
            fetchUserUseCase.invoke().first().also {
                updateState {
                    copy(address = it.address)
                }
            }
            weatherUseCase.invoke().also {
                onTodayWeather(
                    State.WeatherDetail(
                        probability = it.probability,
                        precipitation = it.precipitation,
                        humidity = it.humidity,
                        wind = it.windSpeed,
                        temperature = it.temperature,
                        minTemperature = it.minTemperature,
                        maxTemperature = it.maxTemperature,
                    )
                )
                onWeatherList(
                    it.weather.map { weather ->
                        State.WeatherSimple(
                            weather = weather.weather,
                            minTemperature = weather.minTemp,
                            maxTemperature = weather.maxTemp,
                            day = weather.day
                        )
                    }
                )
            }
        }
    }

    data class State(
        val address: String? = null,
        val todayWeather: WeatherDetail = WeatherDetail(),
        val weatherList: List<WeatherSimple>? = null,
    ) : BaseViewModel.State {
        data class WeatherDetail(
            val probability: Int = 0,
            val precipitation: Int = 0,
            val humidity: Int = 0,
            val wind: Int = 0,
            val temperature: Float = 0f,
            val minTemperature: Float = 0f,
            val maxTemperature: Float = 0f,
        )

        data class WeatherSimple(
            val weather: String = "",
            val minTemperature: Float = 0.0f,
            val maxTemperature: Float = 0f,
            val day: String = "월요일",
        )
    }

    override fun createInitialState(savedState: Parcelable?) = State()
}
