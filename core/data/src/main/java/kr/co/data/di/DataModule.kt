package kr.co.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.data.repository.AccountBookRepositoryImpl
import kr.co.data.repository.AiRepositoryImpl
import kr.co.data.repository.CommunityRepositoryImpl
import kr.co.data.repository.AuthRepositoryImpl
import kr.co.data.repository.DiaryRepositoryImpl
import kr.co.data.repository.FarmWorkRepositoryImpl
import kr.co.data.repository.FcmRepositoryImpl
import kr.co.data.repository.HolidayRepositoryImpl
import kr.co.data.repository.ScheduleRepositoryImpl
import kr.co.data.repository.ServerImageRepositoryImpl
import kr.co.data.repository.SessionRepositoryImpl
import kr.co.data.repository.UserRepositoryImpl
import kr.co.data.repository.WeatherForecastRepositoryImpl
import kr.co.domain.repository.AccountBookRepository
import kr.co.domain.repository.AiRepository
import kr.co.domain.repository.AuthRepository
import kr.co.domain.repository.CommunityRepository
import kr.co.domain.repository.DiaryRepository
import kr.co.domain.repository.FarmWorkRepository
import kr.co.domain.repository.FcmRepository
import kr.co.domain.repository.HolidayRepository
import kr.co.domain.repository.ScheduleRepository
import kr.co.domain.repository.ServerImageRepository
import kr.co.domain.repository.SessionRepository
import kr.co.domain.repository.UserRepository
import kr.co.domain.repository.WeatherForecastRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataModule {
    @Singleton
    @Binds
    abstract fun bindSessionRepository(impl: SessionRepositoryImpl): SessionRepository

    @Singleton
    @Binds
    abstract fun bindWeatherRepository(impl: WeatherForecastRepositoryImpl): WeatherForecastRepository

    @Singleton
    @Binds
    abstract fun bindServerImageRepository(impl: ServerImageRepositoryImpl): ServerImageRepository

    @Singleton
    @Binds
    abstract fun bindCommunityRepository(impl: CommunityRepositoryImpl): CommunityRepository

    @Singleton
    @Binds
    abstract fun bindsFarmWorkRepository(repositoryImpl: FarmWorkRepositoryImpl): FarmWorkRepository

    @Singleton
    @Binds
    abstract fun bindsScheduleRepository(repositoryImpl: ScheduleRepositoryImpl): ScheduleRepository

    @Singleton
    @Binds
    abstract fun bindsDiaryRepository(repositoryImpl: DiaryRepositoryImpl): DiaryRepository

    @Singleton
    @Binds
    abstract fun bindsHolidayRepository(repositoryImpl: HolidayRepositoryImpl): HolidayRepository

    @Singleton
    @Binds
    abstract fun bindsAccountBookRepository(repositoryImpl: AccountBookRepositoryImpl): AccountBookRepository
    @Singleton
    @Binds
    abstract fun bindsAuthRepository(repositoryImpl: AuthRepositoryImpl): AuthRepository
    @Singleton
    @Binds
    abstract fun bindsUserRepository(repositoryImpl: UserRepositoryImpl): UserRepository
    @Singleton
    @Binds
    abstract fun bindsAiChatRepository(repositoryImpl: AiRepositoryImpl): AiRepository
    @Singleton
    @Binds
    abstract fun bindsFcmRepository(repositoryImpl: FcmRepositoryImpl): FcmRepository
}
