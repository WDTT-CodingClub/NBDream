package kr.co.wdtt.nbdream.data.remote.api

import kr.co.wdtt.nbdream.data.remote.model.ApiResult
import kr.co.wdtt.nbdream.data.remote.model.NetWorkRequestInfo
import kr.co.wdtt.nbdream.data.remote.retrofit.NetWorkRequestFactory

class NetworkApi private constructor(
    val netWorkRequestFactory: NetWorkRequestFactory,
    val url: String,
    val headAuth: String,
    val headKey: String,
) {
    companion object {
        fun create(
            netWorkRequestFactory: NetWorkRequestFactory,
            url: String,
            headAuth: String,
            headKey: String,
        ) = NetworkApi(netWorkRequestFactory, url, headAuth, headKey)
    }

    suspend inline fun <reified T: Any> getResponse(
        queryMap: Map<String,String>? = null,
    ) : ApiResult<T> =
        netWorkRequestFactory.create(
            url = url,
            requestInfo = NetWorkRequestInfo.Builder()
                .withHeaders(mapOf(headAuth to headKey))
                .withQueryMap(queryMap?: mapOf())
                .build(),
            type = T::class
        )
}