package kr.co.wdtt.nbdream.data.remote.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Inject

class NetworkFactoryManager @Inject constructor(
    private val headerParser: HeaderParser,
    private val okHttpClient: OkHttpClient,
): INetworkFactoryManager {
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
    ): NetWorkRequestFactory {
        createRetrofit(baseUrl,okHttpClient).create(NetworkService::class.java).let { networkService ->
            return NetWorkRequestFactory.create(networkService, headerParser)
        }
    }
}