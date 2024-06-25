package kr.co.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.co.domain.entity.DiaryEntity
import kr.co.domain.entity.HolidayEntity
import java.time.LocalDate

interface DiaryRepository {
    suspend fun getDiaries(
        crop: String,
        year: Int,
        month: Int
    ): Flow<List<DiaryEntity>>

    suspend fun searchDiaries(
        crop: String,
        query: String,
        startDate: LocalDate,
        endDate: LocalDate
    ): Flow<List<DiaryEntity>>

    suspend fun getDiaryDetail(id:Int): DiaryEntity

    suspend fun createDiary(
        crop: String,
        date: LocalDate,
        holidayList: List<HolidayEntity>,
        weatherForecast: String,
        workLaborer: Int,
        workHours: Int,
        workArea: Int,
        workDescriptions: List<DiaryEntity.WorkDescriptionEntity>,
        memo: String
    )

    suspend fun updateDiary(
        id: Int,
        crop:String,
        date: LocalDate,
        holidayList: List<HolidayEntity>,
        weatherForecast: String,
        workLaborer: Int,
        workHours: Int,
        workArea: Int,
        workDescriptions: List<DiaryEntity.WorkDescriptionEntity>,
        memo: String
    )

    suspend fun deleteDiary(id: Int)
}