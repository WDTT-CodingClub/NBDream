package kr.co.wdtt.nbdream.ui

import javax.inject.Inject

//TODO 로그인 확인 온보딩 or 홈 화면 이동
class MainViewModel @Inject constructor(

): BaseViewModel() {
    override suspend fun onError(errorType: CustomErrorType) {
        TODO("에러 이벤트")
    }

    init {

    }
}