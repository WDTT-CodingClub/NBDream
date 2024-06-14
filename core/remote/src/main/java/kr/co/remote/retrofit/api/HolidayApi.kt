package kr.co.remote.retrofit.api

import kr.co.nbdream.core.remote.BuildConfig
import kr.co.remote.model.response.HolidayResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface HolidayApi {
    @GET("getHoliDeInfo")
    suspend fun getHoliday(
        @Query("serviceKey") key: String = BuildConfig.HOLIDAY_API_KEY,
        @Query("_type") type: String = "json",
        @Query("numOfRows") numOfRows: Int = 100,
        @Query("solYear") year: Int,
        @Query("solMonth") month: Int
    ): HolidayResponse
    @GET("getAnniversaryInfo")
    suspend fun getAnniversary(
        @Query("serviceKey") key: String = BuildConfig.HOLIDAY_API_KEY,
        @Query("_type") type: String = "json",
        @Query("numOfRows") numOfRows: Int = 100,
        @Query("solYear") year: Int,
        @Query("solMonth") month: Int
    ): HolidayResponse
    @GET("get24DivisionInfo")
    suspend fun getSolarTerm(
        @Query("serviceKey") key: String = BuildConfig.HOLIDAY_API_KEY,
        @Query("_type") type: String = "json",
        @Query("numOfRows") numOfRows: Int = 100,
        @Query("solYear") year: Int,
        @Query("solMonth") month: Int
    ): HolidayResponse
    @GET("getSundryDayInfo")
    suspend fun getEtc(
        @Query("serviceKey") key: String = BuildConfig.HOLIDAY_API_KEY,
        @Query("_type") type: String = "json",
        @Query("numOfRows") numOfRows: Int = 100,
        @Query("solYear") year: Int,
        @Query("solMonth") month: Int
    ): HolidayResponse

}