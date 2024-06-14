package kr.co.local.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kr.co.local.SessionLocalDataSourceImpl
import kr.co.data.source.local.SessionLocalDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class LocalModule {
    @Singleton
    @Binds
    abstract fun bindLocalDataSource(local: SessionLocalDataSourceImpl): SessionLocalDataSource
}