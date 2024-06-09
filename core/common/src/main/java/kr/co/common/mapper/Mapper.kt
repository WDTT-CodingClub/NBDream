package kr.co.common.mapper

import kotlinx.coroutines.flow.Flow

interface Mapper<PARAM, RESULT> {
    fun convert(param: PARAM): RESULT
}