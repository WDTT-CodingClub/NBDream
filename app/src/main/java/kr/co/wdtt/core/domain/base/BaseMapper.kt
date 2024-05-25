package kr.co.wdtt.core.domain.base

import kotlinx.coroutines.flow.Flow
import kr.co.wdtt.nbdream.data.mapper.EntityWrapper
import kr.co.wdtt.nbdream.data.remote.model.ApiResponse
import kr.co.wdtt.nbdream.data.remote.model.ApiResult

abstract class BaseMapper<MODEL, ENTITY> {
    fun mapFromResult(result: ApiResult<MODEL>, extra: Any? = null): Flow<EntityWrapper<ENTITY>> =
        when(val response = result.response) {
            is ApiResponse.Success -> getSuccess(response.data, extra)
            is ApiResponse.Fail -> getFailure(response.error)
        }

    abstract fun getSuccess(data: MODEL?, extra: Any?): Flow<EntityWrapper.Success<ENTITY>>

    abstract fun getFailure(error: Throwable): Flow<EntityWrapper.Fail<ENTITY>>
}