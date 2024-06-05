package kr.co.wdtt.core.local.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.wdtt.core.data.source.local.SessionLocalDataSource
import kr.co.wdtt.core.local.SessionLocalDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalModule {
    @Singleton
    @Binds
    abstract fun bindLocalDataSource(local: SessionLocalDataSourceImpl): SessionLocalDataSource
}