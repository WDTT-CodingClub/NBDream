package kr.co.wdtt.nbdream.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kr.co.wdtt.nbdream.data.repository.CalFarmWorkRepoImpl
import kr.co.wdtt.nbdream.domain.repository.CalFarmWorkRepo

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindsCalFarmWorkRepo(repositoryImpl: CalFarmWorkRepoImpl): CalFarmWorkRepo
}
