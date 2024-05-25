package kr.co.wdtt.nbdream.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.wdtt.nbdream.data.remote.retrofit.HeaderParser
import kr.co.wdtt.nbdream.data.remote.retrofit.INetworkFactoryManager
import kr.co.wdtt.nbdream.data.remote.retrofit.NetworkFactoryManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
    @Singleton
    @Provides
    fun provideOkHttpClient(
        interceptor: HttpLoggingInterceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    @Singleton
    @Provides
    fun provideNetworkManager(okHttpClient: OkHttpClient): INetworkFactoryManager
    = NetworkFactoryManager(HeaderParser(),okHttpClient)

}