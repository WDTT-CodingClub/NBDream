package kr.co.wdtt.nbdream.data.remote.retrofit

import kr.co.wdtt.nbdream.data.remote.model.ApiResult
import kr.co.wdtt.nbdream.data.remote.model.NetWorkRequestInfo
import kotlin.reflect.KClass

interface NetWorkRequestFactory {
    suspend fun <T: Any> create(url: String, requestInfo: NetWorkRequestInfo, type: KClass<T>): ApiResult<T>
}