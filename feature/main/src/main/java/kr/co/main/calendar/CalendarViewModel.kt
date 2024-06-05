package kr.co.main.calendar

import dagger.hilt.android.lifecycle.HiltViewModel
import kr.co.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
) : BaseViewModel() {
    private val TAG = this@CalendarViewModel::class.java.simpleName
}