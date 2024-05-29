package kr.co.wdtt.nbdream.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.wdtt.nbdream.data.repository.AccountBookRepositoryImpl
import kr.co.wdtt.nbdream.data.repository.FarmWorkRepositoryImpl
import kr.co.wdtt.nbdream.data.repository.HolidayRepositoryImpl
import kr.co.wdtt.nbdream.data.repository.WeatherForecastRepositoryImpl
import kr.co.wdtt.nbdream.domain.repository.AccountBookRepository
import kr.co.wdtt.nbdream.domain.repository.FarmWorkRepository
import kr.co.wdtt.nbdream.domain.repository.HolidayRepository
import kr.co.wdtt.nbdream.domain.repository.WeatherForecastRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindWeatherRepository(weatherRepository: WeatherForecastRepositoryImpl): WeatherForecastRepository
    @Singleton
    @Binds
    abstract fun bindsFarmWorkRepository(repositoryImpl: FarmWorkRepositoryImpl): FarmWorkRepository
    @Singleton
    @Binds
    abstract fun bindsHolidayRepository(repositoryImpl: HolidayRepositoryImpl): HolidayRepository
    @Singleton
    @Binds
    abstract fun bindsAccountBookRepository(repositoryImpl: AccountBookRepositoryImpl): AccountBookRepository
}