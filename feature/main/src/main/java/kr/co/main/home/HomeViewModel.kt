package kr.co.main.home

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kr.co.domain.usecase.GetDayWeatherForecast
import kr.co.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<HomeViewModel.State>(savedStateHandle) {


    init {
    }

    data class State(
        val state: Any? = null
    ): BaseViewModel.State

    override fun createInitialState(savedState: Parcelable?) = State()
}
