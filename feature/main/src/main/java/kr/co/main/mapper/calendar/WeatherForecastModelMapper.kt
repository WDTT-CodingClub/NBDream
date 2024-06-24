package kr.co.main.mapper.calendar

import kr.co.common.mapper.Mapper
import kr.co.domain.entity.WeatherForecastEntity

internal object WeatherForecastModelMapper
    : Mapper<WeatherForecastEntity, String> {

    override fun convert(param: WeatherForecastEntity): String {
        //TODO WeatherEntity 완성되면, 중기예보 평균값 반환하도록 작업하기
        return "최저온도/최고온도 강수량 하늘상태"
    }
}