package kr.co.main.home

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kr.co.domain.entity.type.ScheduleType
import kr.co.domain.usecase.calendar.GetSchedulesUseCase
import kr.co.domain.usecase.user.FetchUserUseCase
import kr.co.domain.usecase.weather.GetDayWeatherForecastUseCase
import kr.co.main.mapper.home.HomeScheduleUiMapper
import kr.co.ui.base.BaseViewModel
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val fetchUserUseCase: FetchUserUseCase,
    private val weatherUseCase: GetDayWeatherForecastUseCase,
    private val getSchedulesUseCase: GetSchedulesUseCase,
) : BaseViewModel<HomeViewModel.State>(savedStateHandle) {

    private fun onTodayWeather(weather: State.WeatherDetail) = updateState {
        copy(todayWeather = weather)
    }

    private fun onWeatherList(weatherList: List<State.WeatherSimple>) = updateState {
        copy(weatherList = weatherList)
    }

    init {
        viewModelScopeEH.launch {
            fetchUserUseCase.invoke().collect {
                updateState {
                    copy(address = it.address)
                }
            }
        }

        refresh()
    }

    fun refresh() {
        viewModelScopeEH.launch {
            weatherUseCase(currentState.address).also {
                onTodayWeather(
                    State.WeatherDetail(
                        weather = it.weather,
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
                    it.weathers.map { weather ->
                        State.WeatherSimple(
                            weather = weather.weather,
                            minTemperature = weather.minTemp,
                            maxTemperature = weather.maxTemp,
                            day = LocalDate.parse(weather.day)
                        )
                    }
                )
            }
        }

        loadingScope {
            GetSchedulesUseCase.Params.Weekly(
                category = ScheduleType.All,
                weekStartDate = LocalDate.now().toString()
            ).apply {
                getSchedulesUseCase(this).collectLatest {
                    it.map(HomeScheduleUiMapper::convert).also {
                        updateState {
                            copy(schedules = it)
                        }
                    }
                }
            }
        }
    }

    data class State(
        val address: String? = null,
        val todayWeather: WeatherDetail = WeatherDetail(),
        val weatherList: List<WeatherSimple>? = null,
        val schedules: List<Schedule> = emptyList(),
    ) : BaseViewModel.State {
        data class WeatherDetail(
            val weather: String = "",
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
            val day: LocalDate,
        )

        data class Schedule(
            val id: Long,
            val title: String,
            val startDate: LocalDate,
            val endDate: LocalDate,
        )
    }

    override fun createInitialState(savedState: Parcelable?) = State()
}
