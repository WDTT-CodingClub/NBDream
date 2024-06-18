package kr.co.local.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.local.SessionLocalDataSourceImpl
import kr.co.data.source.local.SessionLocalDataSource
import kr.co.data.source.local.UserLocalDataSource
import kr.co.local.UserLocalDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class LocalModule {
    @Singleton
    @Binds
    abstract fun bindSessionDataSource(local: SessionLocalDataSourceImpl): SessionLocalDataSource

    @Singleton
    @Binds
    abstract fun bindUserDataSource(local: UserLocalDataSourceImpl): UserLocalDataSource
}