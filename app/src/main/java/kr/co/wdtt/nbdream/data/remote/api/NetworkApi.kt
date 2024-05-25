package kr.co.wdtt.nbdream.data.remote.api

import kr.co.wdtt.nbdream.data.remote.model.ApiResult
import kr.co.wdtt.nbdream.data.remote.model.NetWorkRequestInfo
import kr.co.wdtt.nbdream.data.remote.retrofit.NetWorkRequestFactory

class NetworkApi private constructor(
    private val netWorkRequestFactory: NetWorkRequestFactory,
    private val headAuth: String,
    private val headKey: String,
) {
    companion object {
        fun create(
            netWorkRequestFactory: NetWorkRequestFactory,
            headAuth: String,
            headKey: String,
        ) = NetworkApi(netWorkRequestFactory, headAuth, headKey)

    }

    internal suspend inline fun <reified T : Any> sendRequest(
        url: String? = null,
        queryMap: Map<String, String>? = null,
    ): ApiResult<T> =
        netWorkRequestFactory.create(
            url = url?: "",
            requestInfo = NetWorkRequestInfo.Builder()
                .withHeaders(mapOf(headAuth to headKey))
                .withQueryMap(queryMap ?: mapOf())
                .build(),
            type = T::class
        )
}