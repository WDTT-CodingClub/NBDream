package kr.co.main.model.calendar

import kr.co.common.mapper.Mapper
import timber.log.Timber
import java.time.LocalDate

internal data class DiaryStreakModel(
    val startDate: LocalDate,
    val endDate: LocalDate,
    val diaries: List<DiaryModel> = emptyList()
)

internal object DiaryStreakMapper : Mapper<List<DiaryModel>, List<DiaryStreakModel>> {
    override fun convert(param: List<DiaryModel>): List<DiaryStreakModel> {
        val diaryStreaks = mutableListOf<DiaryStreakModel>()
        val streak = mutableListOf<DiaryModel>()

        param.sortedBy { it.date }.forEachIndexed { index, diary ->
            if (streak.isEmpty() || (streak.last().date.plusDays(1) == diary.date)) {
                streak.add(diary)
            } else {
                diaryStreaks.add(
                    DiaryStreakModel(
                        startDate = streak.first().date,
                        endDate = streak.last().date,
                        diaries = streak
                    )
                )
                streak.clear()
                streak.add(diary)
            }
        }
        if (streak.isNotEmpty())
            diaryStreaks.add(
                DiaryStreakModel(
                    startDate = streak.first().date,
                    endDate = streak.last().date,
                    diaries = streak
                )
            )

        Timber.d("diaries: ${param.map{it.date}}")
        Timber.d("diaryStreaks: ${diaryStreaks.map{it.startDate to it.endDate}}")
        return diaryStreaks
    }
}