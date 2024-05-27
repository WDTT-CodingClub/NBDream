package kr.co.wdtt.nbdream.data.remote.api

import android.util.Log
import kr.co.wdtt.nbdream.data.remote.model.ApiResult
import kr.co.wdtt.nbdream.data.remote.model.NetWorkRequestInfo
import kr.co.wdtt.nbdream.data.remote.retrofit.NetWorkRequestFactory

class NetworkApi private constructor(
    private val netWorkRequestFactory: NetWorkRequestFactory,
    private val headAuth: String,
    private val headKey: String,
    private val paramAuth: String,
    private val paramKey: String,
) {
    companion object {
        fun create(
            netWorkRequestFactory: NetWorkRequestFactory,
            headAuth: String = "",
            headKey: String = "",
            paramAuth: String = "",
            paramKey: String = "",
        ): NetworkApi {
            Log.d("NetworkApi", "create) headAuth : $headAuth, headKey : $headKey, paramAuth : $paramAuth, paramKey : $paramKey")
            return NetworkApi(netWorkRequestFactory, headAuth, headKey, paramAuth, paramKey)
        }
    }

    internal suspend inline fun <reified T : Any> sendRequest(
        url: String? = null,
        queryMap: Map<String, String>? = null,
    ): ApiResult<T> =
        netWorkRequestFactory.create(
            url = url ?: "",
            requestInfo = NetWorkRequestInfo.Builder()
                .apply {
                    if (headAuth.isNotEmpty() && headKey.isNotEmpty()) {
                        withHeaders(mapOf(headAuth to headKey))
                    }
                }
                .apply {
                    withQueryMap(
                        (queryMap ?: mapOf())
                            .toMutableMap()
                            .apply {
                                if (paramAuth.isNotEmpty() && paramKey.isNotEmpty())
                                    put(paramAuth, paramKey)

                                Log.d("NetworkApi", "queryMap : $this")
                            }
                    )
                }
                .build(),
            type = T::class
        )
}