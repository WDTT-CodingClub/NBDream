package kr.co.wdtt.nbdream.domain.usecase.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.wdtt.nbdream.domain.usecase.auth.FetchAuthUseCase
import kr.co.wdtt.nbdream.domain.usecase.auth.FetchAuthUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {
    @Singleton
    @Binds
    abstract fun bindFetchAuthUseCase(impl: FetchAuthUseCaseImpl): FetchAuthUseCase
}