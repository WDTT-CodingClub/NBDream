package kr.co.wdtt.nbdream.data.repository

import kotlinx.coroutines.flow.Flow
import kr.co.wdtt.nbdream.BuildConfig
import kr.co.wdtt.nbdream.data.mapper.EntityWrapper
import kr.co.wdtt.nbdream.data.mapper.FarmWorkMapper
import kr.co.wdtt.nbdream.data.remote.dto.FarmWorkResponse
import kr.co.wdtt.nbdream.data.remote.retrofit.NetworkFactoryManager
import kr.co.wdtt.nbdream.domain.entity.FarmWorkEntity
import kr.co.wdtt.nbdream.domain.repository.FarmWorkRepository
import javax.inject.Inject

internal class FarmWorkRepositoryImpl @Inject constructor(
    network: NetworkFactoryManager,
    private val mapper: FarmWorkMapper
) : FarmWorkRepository {
    companion object {
        private const val BASE_URL = "http://api.nongsaro.go.kr/service/farmWorkingPlanNew/"
        private const val FARM_WORK = "workScheduleEraInfoJsonLst"

        private const val PARAM_AUTH = "apiKey"
        private const val PARAM_KEY = BuildConfig.NONGSARO_API_KEY
    }

    private val networkApi = network.create(
        baseUrl = BASE_URL
    )
    private val queryMap = mutableMapOf<String, String>(
        PARAM_AUTH to PARAM_KEY
    )

    override suspend fun getFarmWorks(
        crop: String,
        year: Int,
        month: Int
    ): Flow<List<FarmWorkEntity>> {
        TODO("Not yet implemented")
    }
}