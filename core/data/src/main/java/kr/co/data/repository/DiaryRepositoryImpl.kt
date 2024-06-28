package kr.co.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import kr.co.data.mapper.DiaryMapper
import kr.co.data.mapper.HolidayMapper
import kr.co.data.source.remote.DiaryRemoteDataSource
import kr.co.domain.entity.DiaryEntity
import kr.co.domain.entity.HolidayEntity
import kr.co.domain.repository.DiaryRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

internal class DiaryRepositoryImpl @Inject constructor(
    private val remote: DiaryRemoteDataSource
) : DiaryRepository {
    override suspend fun getDiaries(crop: String, year: Int, month: Int): Flow<List<DiaryEntity>> =
        remote.fetchList(
            crop = crop,
            year = String.format("%02d", year),
            month = String.format("%02d", month)
        ).transform {
            emit(
                it.map { diaryData -> DiaryMapper.convert(diaryData) }
            )
        }

    override suspend fun searchDiaries(
        crop: String,
        query: String,
        startDate: LocalDate,
        endDate: LocalDate
    ): Flow<List<DiaryEntity>> =
        remote.searchList(
            crop = crop,
            query = query,
            startDate = startDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")),
            endDate = endDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        ).transform {
            emit(
                it.map { diaryData -> DiaryMapper.convert(diaryData) }
            )
        }

    override suspend fun getDiaryDetail(id: Int): DiaryEntity =
        remote.fetchDetail(id).let{
            DiaryMapper.convert(it)
        }

    override suspend fun createDiary(
        crop: String,
        date: LocalDate,
        holidayList: List<HolidayEntity>,
        weatherForecast: String,
        imageUrls: List<String>,
        workLaborer: Int,
        workHours: Int,
        workArea: Int,
        workDescriptions: List<DiaryEntity.WorkDescriptionEntity>,
        memo: String
    ) {
        remote.create(
            crop = crop,
            date = date,
            holidayList = holidayList.map {
                HolidayMapper.toLeft(it)
            },
            weatherForecast = weatherForecast,
            imageUrls = imageUrls,
            workLaborer = workLaborer,
            workHours = workHours,
            workArea = workArea,
            workDescriptions = workDescriptions.map {
                DiaryMapper.WorkDescriptionMapper.toLeft(it)
            },
            memo = memo
        )
    }

    override suspend fun updateDiary(
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
    ) {
        remote.update(
            id = id,
            crop = crop,
            date = date.format(DateTimeFormatter.ofPattern("yyyyMMdd")),
            holidayList = holidayList.map {
                HolidayMapper.toLeft(it)
            },
            weatherForecast = weatherForecast,
            workLaborer = workLaborer,
            workHours = workHours,
            workArea = workArea,
            workDescriptions = workDescriptions.map {
                DiaryMapper.WorkDescriptionMapper.toLeft(it)
            },
            memo = memo
        )
    }

    override suspend fun deleteDiary(id: Int) {
        remote.delete(id)
    }
}