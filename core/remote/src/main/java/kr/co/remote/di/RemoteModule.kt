package kr.co.remote.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import kr.co.data.source.remote.AccountBookRemoteDataSource
import kr.co.data.source.remote.AuthRemoteDataSource
import kr.co.data.source.remote.HolidayRemoteDataSource
import kr.co.data.source.remote.ServerImageRemoteDataSource
import kr.co.data.source.remote.WeatherRemoteDataSource
import kr.co.remote.AccountBookRemoteDataSourceImpl
import kr.co.remote.AuthRemoteDataSourceImpl
import kr.co.remote.HolidayRemoteDataSourceImpl
import kr.co.remote.ServerImageRemoteDataSourceImpl
import kr.co.remote.WeatherRemoteDataSourceImpl
import kr.co.remote.retrofit.api.HolidayApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class RemoteModule {
    @Singleton
    @Provides
    fun provideWeatherApi(
        client: HttpClient,
    ): WeatherRemoteDataSource = WeatherRemoteDataSourceImpl(client)

    @Singleton
    @Provides
    fun provideServerImageApi(
        client: HttpClient,
    ): ServerImageRemoteDataSource = ServerImageRemoteDataSourceImpl(client)

    @Singleton
    @Provides
    fun provideHolidayApi(
        api: HolidayApi,
    ): HolidayRemoteDataSource = HolidayRemoteDataSourceImpl(api)

    @Singleton
    @Provides
    fun provideAccountBookApi(
        client: HttpClient,
    ): AccountBookRemoteDataSource = AccountBookRemoteDataSourceImpl(client)

    @Singleton
    @Provides
    fun provideAuthApi(
        client: HttpClient,
    ): AuthRemoteDataSource = AuthRemoteDataSourceImpl(client)
}