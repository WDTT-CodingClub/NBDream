package kr.co.wdtt.nbdream.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kr.co.wdtt.nbdream.BuildConfig
import kr.co.wdtt.nbdream.data.mapper.EntityWrapper
import kr.co.wdtt.nbdream.data.mapper.HolidayMapper
import kr.co.wdtt.nbdream.data.remote.dto.HolidayResponse
import kr.co.wdtt.nbdream.data.remote.retrofit.NetworkFactoryManager
import kr.co.wdtt.nbdream.domain.entity.HolidayEntity
import kr.co.wdtt.nbdream.domain.repository.HolidayRepository
import javax.inject.Inject

internal class HolidayRepositoryImpl @Inject constructor(
    network: NetworkFactoryManager,
    private val mapper: HolidayMapper
) : HolidayRepository {
    companion object {
        private const val BASE_URL = "http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/"
        private const val HOLIDAY = "getHoliDeInfo"
        private const val ANNIVERSARY = "getAnniversaryInfo"
        private const val SOLAR_TERM = "get24DivisionInfo"
        private const val ETC = "getSundryDayInfo"

        private const val PARAM_AUTH = "ServiceKey"
        private const val PARAM_KEY = BuildConfig.HOLIDAY_API_KEY
    }

    private val networkApi = network.create(
        baseUrl = BASE_URL,
    )
    private val queryMap = mutableMapOf(
        PARAM_AUTH to PARAM_KEY,
        "_type" to "json",
        "numOfRows" to "100"
    )

    override suspend fun getHoliday(year: Int, month: Int): Flow<EntityWrapper<List<HolidayEntity>>> =
       sendHolidayRequest(HOLIDAY, year, month)
    override suspend fun getAnniversary(year: Int, month: Int): Flow<EntityWrapper<List<HolidayEntity>>> =
        sendHolidayRequest(ANNIVERSARY, year, month)
    override suspend fun getSolarTerm(year: Int, month: Int): Flow<EntityWrapper<List<HolidayEntity>>> =
        sendHolidayRequest(SOLAR_TERM, year, month)
    override suspend fun getEtc(year: Int, month: Int): Flow<EntityWrapper<List<HolidayEntity>>> =
        sendHolidayRequest(ETC, year, month)

    private suspend fun sendHolidayRequest(endPoint:String, year: Int, month: Int) = mapper.mapFromResult(
        networkApi.sendRequest<HolidayResponse>(
            url = endPoint,
            queryMap = queryMap.apply {
                put("solYear", year.toString())
                put("solMonth", month.toString().padStart(2, '0'))
            }
        )
    )
}