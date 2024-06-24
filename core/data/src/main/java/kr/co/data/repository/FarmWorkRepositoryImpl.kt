package kr.co.data.repository

import kr.co.data.mapper.FarmWorkMapper
import kr.co.data.source.remote.FarmWorkRemoteDataSource
import kr.co.domain.entity.FarmWorkEntity
import kr.co.domain.repository.FarmWorkRepository
import javax.inject.Inject

internal class FarmWorkRepositoryImpl @Inject constructor(
    private val remoteDataSource: FarmWorkRemoteDataSource,
) : FarmWorkRepository {
    override suspend fun getFarmWorks(crop: String, month: Int): List<FarmWorkEntity> =
        remoteDataSource.fetchList(crop, month).map {
            FarmWorkMapper.convert(it)
        }
}