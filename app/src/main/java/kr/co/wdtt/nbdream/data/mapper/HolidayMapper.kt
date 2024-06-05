package kr.co.wdtt.nbdream.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kr.co.wdtt.core.domain.base.BaseMapper
import kr.co.wdtt.nbdream.data.remote.dto.HolidayResponse
import kr.co.wdtt.nbdream.domain.entity.HolidayEntity
import kr.co.wdtt.nbdream.domain.entity.HolidayType
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

internal class HolidayMapper @Inject constructor() :
    BaseMapper<HolidayResponse, List<HolidayEntity>>() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getSuccess(
        data: HolidayResponse?,
        extra: Any?
    ): Flow<EntityWrapper.Success<List<HolidayEntity>>> = flow {
        data?.let {
            emit(
                EntityWrapper.Success(
                    data = it.response.body.items.item.map { item ->
                        item.convert()
                    }
                )
            )
        } ?: emit(EntityWrapper.Success(emptyList()))
    }

    override fun getFailure(error: Throwable): Flow<EntityWrapper.Fail<List<HolidayEntity>>> =
        flow { emit(EntityWrapper.Fail(error)) }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun HolidayResponse.Response.Body.Item.convert(): HolidayEntity =
        HolidayEntity(
            date = LocalDate.parse(date.toString(), DateTimeFormatter.ofPattern("yyyymmdd")),
            isHoliday = (isHoliday == "Y"),
            type = mapHolidayType(dateType, isHoliday),
            name = dateName
        )

    private fun mapHolidayType(dateType: String, isHoliday: String): HolidayType =
        when (dateType) {
            "01" -> if (isHoliday == "Y") HolidayType.NATIONAL_HOLIDAY else HolidayType.CONSTITUTION_DAY
            "02" -> HolidayType.ANNIVERSARY
            "03" -> HolidayType.SOLAR_TERM
            else -> HolidayType.ETC
        }
}