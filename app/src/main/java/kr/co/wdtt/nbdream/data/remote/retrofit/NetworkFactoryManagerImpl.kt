package kr.co.wdtt.nbdream.data.remote.retrofit

import kr.co.wdtt.nbdream.data.remote.api.NetworkApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Inject

class NetworkFactoryManagerImpl @Inject constructor(
    private val headerParser: HeaderParser,
    private val okHttpClient: OkHttpClient,
) : NetworkFactoryManager {
    private fun createRetrofit(
        baseUrl: String,
        okHttpClient: OkHttpClient,
    ) = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(StringConverterFactory())
        .client(okHttpClient)
        .build()

    override fun create(
        baseUrl: String,
        headAuth: String?,
        headKey: String?
    ) = createRetrofit(baseUrl, okHttpClient).create(NetworkService::class.java)
        .let { networkService ->
            NetWorkRequestFactory.create(networkService, headerParser).let { factory ->
                NetworkApi.create(
                    factory, headAuth, headKey
                )
            }
        }
}