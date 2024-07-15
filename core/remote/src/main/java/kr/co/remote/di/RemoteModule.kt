package kr.co.remote.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import kr.co.data.source.remote.AccountBookRemoteDataSource
import kr.co.data.source.remote.AiRemoteDataSource
import kr.co.data.source.remote.AuthRemoteDataSource
import kr.co.data.source.remote.CommunityRemoteDataSource
import kr.co.data.source.remote.DiaryRemoteDataSource
import kr.co.data.source.remote.FarmWorkRemoteDataSource
import kr.co.data.source.remote.FcmRemoteDataSource
import kr.co.data.source.remote.HolidayRemoteDataSource
import kr.co.data.source.remote.ScheduleRemoteDataSource
import kr.co.data.source.remote.ServerImageRemoteDataSource
import kr.co.data.source.remote.UserRemoteDataSource
import kr.co.data.source.remote.WeatherRemoteDataSource
import kr.co.remote.AccountBookRemoteDataSourceImpl
import kr.co.remote.AiRemoteDataSourceImpl
import kr.co.remote.AuthRemoteDataSourceImpl
import kr.co.remote.CommunityRemoteDataSourceImpl
import kr.co.remote.DiaryRemoteDataSourceImpl
import kr.co.remote.FarmWorkRemoteDataSourceImpl
import kr.co.remote.FcmRemoteDataSourceImpl
import kr.co.remote.HolidayRemoteDataSourceImpl
import kr.co.remote.ScheduleRemoteDataSourceImpl
import kr.co.remote.ServerImageRemoteDataSourceImpl
import kr.co.remote.UserRemoteDataSourceImpl
import kr.co.remote.WeatherRemoteDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class RemoteModule {
    @Singleton
    @Provides
    fun provideAuthApi(
        client: HttpClient
    ): AuthRemoteDataSource = AuthRemoteDataSourceImpl(client)

    @Singleton
    @Provides
    fun provideWeatherApi(
        client: HttpClient
    ): WeatherRemoteDataSource = WeatherRemoteDataSourceImpl(client)

    @Singleton
    @Provides
    fun provideServerImageApi(
        client: HttpClient
    ): ServerImageRemoteDataSource = ServerImageRemoteDataSourceImpl(client)

    @Singleton
    @Provides
    fun provideFarmWorkApi(
        client: HttpClient
    ): FarmWorkRemoteDataSource = FarmWorkRemoteDataSourceImpl(client)

    @Singleton
    @Provides
    fun provideDiaryApi(
        client: HttpClient
    ): DiaryRemoteDataSource = DiaryRemoteDataSourceImpl(client)

    @Singleton
    @Provides
    fun provideScheduleApi(
        client: HttpClient
    ): ScheduleRemoteDataSource = ScheduleRemoteDataSourceImpl(client)

    @Singleton
    @Provides
    fun providesHolidayApi(
        client: HttpClient
    ): HolidayRemoteDataSource = HolidayRemoteDataSourceImpl(client)

    @Singleton
    @Provides
    fun provideAccountBookApi(
        client: HttpClient
    ): AccountBookRemoteDataSource = AccountBookRemoteDataSourceImpl(client)

    @Singleton
    @Provides
    fun provideCommunityApi(
        client: HttpClient
    ): CommunityRemoteDataSource = CommunityRemoteDataSourceImpl(client)


    @Singleton
    @Provides
    fun provideUserApi(
        client: HttpClient
    ): UserRemoteDataSource = UserRemoteDataSourceImpl(client)

    @Singleton
    @Provides
    fun provideAiApi(
        client: HttpClient
    ): AiRemoteDataSource = AiRemoteDataSourceImpl(client)

    @Singleton
    @Provides
    fun provideNotificationApi(
        client: HttpClient
    ): FcmRemoteDataSource = FcmRemoteDataSourceImpl(client)
}