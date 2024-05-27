package kr.co.wdtt.nbdream.ui.calendar

import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kr.co.wdtt.core.ui.base.BaseViewModel
import kr.co.wdtt.core.ui.base.CustomErrorType
import kr.co.wdtt.nbdream.domain.usecase.GetFarmWorkUseCase
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getFarmWorkUseCase:GetFarmWorkUseCase
) : BaseViewModel() {
    private val TAG = this@CalendarViewModel::class.java.simpleName

    override suspend fun onError(errorType: CustomErrorType) {
        //TODO("Not yet implemented")
    }
    init {
        viewModelScopeEH.launch {
            farmWorkApiTest()
        }
    }

    private suspend fun farmWorkApiTest() {
        getFarmWorkUseCase.invoke("30697").collect{
            Log.d(TAG, "$it")
        }
    }
}