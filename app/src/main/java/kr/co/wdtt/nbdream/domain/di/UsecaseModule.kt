package kr.co.wdtt.nbdream.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.wdtt.nbdream.domain.usecase.GetCalendarDate
import kr.co.wdtt.nbdream.domain.usecase.GetCalendarDateImpl
import kr.co.wdtt.nbdream.domain.usecase.GetDayWeatherForecastImpl
import kr.co.wdtt.nbdream.domain.usecase.GetDayWeatherForecast
import kr.co.wdtt.nbdream.domain.usecase.GetFarmWork
import kr.co.wdtt.nbdream.domain.usecase.GetFarmWorkImpl
import kr.co.wdtt.nbdream.domain.usecase.GetHoliday
import kr.co.wdtt.nbdream.domain.usecase.GetHolidayImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class UsecaseModule {
    @Binds
    @Singleton
    abstract fun bindGetDayWeatherForecast(usecase: GetDayWeatherForecastImpl): GetDayWeatherForecast
    @Binds
    @Singleton
    abstract fun bindGetFarmWork(usecase: GetFarmWorkImpl): GetFarmWork
    @Binds
    @Singleton
    abstract fun bindGetHoliday(usecase: GetHolidayImpl): GetHoliday
    @Binds
    @Singleton
    abstract fun bindGetCalendar(usecase: GetCalendarDateImpl): GetCalendarDate
}