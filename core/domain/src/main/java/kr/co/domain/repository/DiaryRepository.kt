package kr.co.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.co.domain.entity.DiaryEntity

interface DiaryRepository {
    fun getDiaries(
        crop:String,
        year:Int,
        month:Int
    ): Flow<List<DiaryEntity>>

    fun searchDiaries(
        crop:String,
        query:String,
        startDate:String,
        endDate:String
    ): Flow<List<DiaryEntity>>

    // TODO 추가/편집/삭제
}