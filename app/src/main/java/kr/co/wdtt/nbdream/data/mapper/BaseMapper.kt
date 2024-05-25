package kr.co.wdtt.nbdream.data.mapper

import kr.co.wdtt.nbdream.data.remote.model.ApiResponse
import kr.co.wdtt.nbdream.data.remote.model.ApiResult

abstract class BaseMapper<MODEL, ENTITY> {
    fun mapFromResult(result: ApiResult<MODEL>, extra: Any? = null): EntityWrapper<ENTITY> =
        when(val reponse = result.response) {
            is ApiResponse.Success -> getSuccess(reponse.data, extra)
            is ApiResponse.Fail -> getFailure(reponse.error)
        }

    abstract fun getSuccess(data: MODEL, extra: Any?): EntityWrapper.Success<ENTITY>

    abstract fun getFailure(error: Throwable): EntityWrapper.Fail<ENTITY>
}