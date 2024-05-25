package kr.co.wdtt.nbdream.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.wdtt.nbdream.data.remote.retrofit.NetworkService
import kr.co.wdtt.nbdream.data.remote.retrofit.HeaderParser
import kr.co.wdtt.nbdream.data.remote.retrofit.NetWorkRequestFactory
import kr.co.wdtt.nbdream.data.remote.retrofit.NetWorkRequestFactoryImpl
import kr.co.wdtt.nbdream.data.remote.retrofit.StringConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
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

    @Singleton
    @Provides
    fun providesNetworkService(retrofit: Retrofit) = retrofit.create(NetworkService::class.java)

    @Singleton
    @Provides
    fun provideRequestFactory(remote: NetworkService, headerParser: HeaderParser): NetWorkRequestFactory
    = NetWorkRequestFactoryImpl(remote,headerParser)
}