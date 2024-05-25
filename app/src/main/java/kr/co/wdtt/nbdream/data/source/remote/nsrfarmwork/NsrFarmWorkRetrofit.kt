package kr.co.wdtt.nbdream.data.source.remote.nsrfarmwork

import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import kr.co.wdtt.nbdream.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object NsrWorkScheduleRetrofit {
    private const val BASE_URL = "http://api.nongsaro.go.kr/service/farmWorkingPlanNew"

    private val NsrWorkScheduleRetrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(
            TikXmlConverterFactory.create(
                TikXml.Builder().exceptionOnUnreadXml(false).build()
            )
        )
        .client(createOkHttpClient())
        .build()

    private fun createOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(NsrInterceptor())
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addNetworkInterceptor(interceptor)
            .build()
    }

    val nsrFarmWorkApi =
        NsrWorkScheduleRetrofit.create(NsrFarmWorkApi::class.java)
}

class NsrInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val auth = BuildConfig.NONGSARO_API_KEY

        builder.addHeader("apiKey", auth)
        return chain.proceed(builder.build())
    }
}