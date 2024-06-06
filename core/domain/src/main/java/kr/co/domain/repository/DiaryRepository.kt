package kr.co.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.co.domain.entity.DiaryEntity

interface DiaryRepository {
    suspend fun getDiaries(
        crop:String,
        year:Int,
        month:Int
    ): Flow<List<DiaryEntity>>

    suspend fun searchDiaries(
        crop:String,
        query:String,
        startDate:String,
        endDate:String
    ): Flow<List<DiaryEntity>>

    suspend fun getDiaryId(): String

    suspend fun addDiary(diaryEntity: DiaryEntity)
    suspend fun editDiary(diaryEntity: DiaryEntity)
    suspend fun deleteDiary(diaryId:String)
}