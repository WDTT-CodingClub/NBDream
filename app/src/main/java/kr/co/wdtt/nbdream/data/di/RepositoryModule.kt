package kr.co.wdtt.nbdream.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.wdtt.nbdream.data.repository.WeatherForecastRepositoryImpl
import kr.co.wdtt.nbdream.domain.repository.WeatherForecastRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindWeatherRepository(weatherRepository: WeatherForecastRepositoryImpl): WeatherForecastRepository

}