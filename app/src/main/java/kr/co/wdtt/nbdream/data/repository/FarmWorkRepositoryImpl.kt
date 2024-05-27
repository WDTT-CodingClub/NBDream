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
        const val BASE_URL = "http://api.nongsaro.go.kr/service/farmWorkingPlanNew"
        const val FARM_WORK = "workScheduleEraInfoJsonLst"
        const val HEAD_AUTH = "apiKey"
        const val HEAD_KEY = BuildConfig.NONGSARO_API_KEY
    }

    private val queryMap = mutableMapOf<String, String>()

    private val networkApi = network.create(
        BASE_URL,
        HEAD_AUTH,
        HEAD_KEY
    )

    override suspend fun getFarmWorkByCrop(cropCode: String): Flow<EntityWrapper<List<FarmWorkEntity>>> =
        mapper.mapFromResult(
            networkApi.sendRequest<FarmWorkResponse>(
                url = FARM_WORK,
                queryMap = queryMap.apply {
                    put("cntntsNo", cropCode)
                }
            )
        )
}