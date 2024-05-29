package kr.co.wdtt.nbdream.ui.calendar

import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.co.wdtt.core.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
) : BaseViewModel() {
    private val TAG = this@CalendarViewModel::class.java.simpleName
}