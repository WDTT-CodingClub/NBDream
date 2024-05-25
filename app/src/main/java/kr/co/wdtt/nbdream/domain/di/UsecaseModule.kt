package kr.co.wdtt.nbdream.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.wdtt.nbdream.domain.usecase.GetDayWeatherForecast
import kr.co.wdtt.nbdream.domain.usecase.IGetDayWeatherForecast
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class UsecaseModule {
    @Binds
    @Singleton
    abstract fun bindgetDayUsecase(usecase: GetDayWeatherForecast): IGetDayWeatherForecast
}