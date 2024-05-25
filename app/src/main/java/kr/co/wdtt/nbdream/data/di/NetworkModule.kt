package kr.co.wdtt.nbdream.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.wdtt.nbdream.data.remote.api.NetworkService
import kr.co.wdtt.nbdream.data.remote.retrofit.StringConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun provideOkHttpClient(
        interceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
//        if (BuildConfig.DEBUG) {
//            builder.addInterceptor(interceptor)
//        }
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
    ) = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(StringConverterFactory())
        .build()
        .create(NetworkService::class.java)
}