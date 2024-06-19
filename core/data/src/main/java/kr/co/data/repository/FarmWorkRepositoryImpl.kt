package kr.co.data.repository

import kotlinx.coroutines.flow.Flow
import kr.co.domain.entity.FarmWorkEntity
import kr.co.domain.repository.FarmWorkRepository
import javax.inject.Inject

internal class FarmWorkRepositoryImpl @Inject constructor(
    //remoteDataSource: FarmWorkRemoteDataSource,
) : FarmWorkRepository{
    override fun getFarmWorks(crop: String, year: Int, month: Int): Flow<List<FarmWorkEntity>> {
        TODO("Not yet implemented")
    }
}