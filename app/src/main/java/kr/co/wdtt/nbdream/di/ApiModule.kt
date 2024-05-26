package kr.co.wdtt.nbdream.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.wdtt.nbdream.data.source.remote.nsrfarmwork.NsrFarmWorkApi
import kr.co.wdtt.nbdream.data.source.remote.nsrfarmwork.NsrFarmWorkRetrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Singleton
    @Provides
    fun provideNsrWorkScheduleApi(): NsrFarmWorkApi = NsrFarmWorkRetrofit.nsrFarmWorkApi
}