package kr.co.data.repository

import kr.co.domain.entity.FarmWorkEntity
import kr.co.domain.repository.FarmWorkRepository
import javax.inject.Inject

internal class FarmWorkRepositoryImpl @Inject constructor(
) : FarmWorkRepository {
    //    companion object {
//        private const val BASE_URL = "http://api.nongsaro.go.kr/service/farmWorkingPlanNew/"
//        private const val FARM_WORK = "workScheduleEraInfoJsonLst"
//
//        private const val PARAM_AUTH = "apiKey"
//        private const val PARAM_KEY = BuildConfig.NONGSARO_API_KEY
//    }
//
//    private val networkApi = network.create(
//        baseUrl = BASE_URL
//    )
//    private val queryMap = mutableMapOf<String, String>(
//        PARAM_AUTH to PARAM_KEY
//    )
//
//    override suspend fun getFarmWorkByCrop(cropCode: String): Flow<EntityWrapper<List<FarmWorkEntity>>> =
//        mapper.mapFromResult(
//            networkApi.sendRequest<FarmWorkResponse>(
//                url = FARM_WORK,
//                queryMap = queryMap.apply {
//                    put("cntntsNo", cropCode)
//                }
//            )
//        )
    override suspend fun getFarmWorkByCrop(cropCode: String): List<FarmWorkEntity> {
        TODO("Not yet implemented")
    }
}