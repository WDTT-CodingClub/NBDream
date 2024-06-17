package kr.co.common.mapper

interface Mapper<PARAM, RESULT> {
    fun convert(param: PARAM): RESULT
}