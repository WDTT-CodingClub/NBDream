package kr.co.wdtt.nbdream.data.remote.api

import kr.co.wdtt.nbdream.data.remote.model.ApiResult
import kr.co.wdtt.nbdream.data.remote.model.NetWorkRequestInfo
import kr.co.wdtt.nbdream.data.remote.model.RequestType
import kr.co.wdtt.nbdream.data.remote.retrofit.NetWorkRequestFactory

class NetworkApi private constructor(
    private val netWorkRequestFactory: NetWorkRequestFactory,
    private val headAuth: String? = null,
    private val headKey: String? = null
) {
    companion object {
        fun create(
            netWorkRequestFactory: NetWorkRequestFactory,
            headAuth: String? = null,
            headKey: String? = null
        ) = NetworkApi(netWorkRequestFactory, headAuth, headKey)
    }

    internal suspend inline fun <reified T : Any> sendRequest(
        url: String? = null,
        queryMap: Map<String, String>? = null,
        method: RequestType = RequestType.GET,
        body: Any? = null
    ): ApiResult<T> =
        netWorkRequestFactory.create(
            url = url ?: "",
            requestInfo = NetWorkRequestInfo.Builder(method)
                .apply {
                    if (headAuth != null && headKey != null) {
                        withHeaders(mapOf(headAuth to headKey))
                    }
                }
                .withQueryMap(queryMap ?: mapOf())
                .apply { if (body != null) withBody(body) }
                .build(),
            type = T::class
        )
}