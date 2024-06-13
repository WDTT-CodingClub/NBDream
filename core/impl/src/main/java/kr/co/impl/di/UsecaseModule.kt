package kr.co.impl.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.domain.usecase.*
import kr.co.impl.FetchAuthUseCaseImpl
import kr.co.impl.GetCalendarDateImpl
import kr.co.impl.GetDayWeatherForecastImpl
import kr.co.impl.LoginUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class UsecaseModule {
    @Binds
    @Singleton
    abstract fun bindGetDayWeatherForecast(usecase: GetDayWeatherForecastImpl): GetDayWeatherForecast
//    @Binds
//    @Singleton
//    abstract fun bindGetFarmWork(usecase: GetFarmWorkImpl): GetFarmWork
//    @Binds
//    @Singleton
//    abstract fun bindGetHoliday(usecase: GetHolidayImpl): GetHoliday
    @Binds
    @Singleton
    abstract fun bindGetCalendar(usecase: GetCalendarDateImpl): GetCalendarDate
    @Singleton
    @Binds
    abstract fun bindFetchAuthUseCase(impl: FetchAuthUseCaseImpl): FetchAuthUseCase
    @Singleton
    @Binds
    abstract fun bindLoginUseCase(impl: LoginUseCaseImpl): LoginUseCase
}