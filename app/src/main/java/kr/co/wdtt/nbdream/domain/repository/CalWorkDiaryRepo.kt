package kr.co.wdtt.nbdream.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.co.wdtt.nbdream.domain.entity.CalWorkDiaryEntity

interface CalWorkDiaryRepo {
    //TODO 서버에 작물 정보 어떤 형태로 보내야 할지 상의
    suspend fun getCalendarWorkDiarys(userId:String, cropCode: String, year:Int, month:Int):
            Flow<List<CalWorkDiaryEntity>>

    // TODO 추가/편집 요청 따로 해야 하는지 상의
    suspend fun upsertCalendarWorkDiary(calWorkDiaryEntity: CalWorkDiaryEntity)
    suspend fun deleteCalendarWorkDiary(calendarWorkDiaryId: String)

    suspend fun searchCalendarWorkDiary(
        userId:String,
        keyword:String,
        startYear:Int,
        startMonth:Int,
        startDay:Int,
        endYear:Int,
        endMonth:Int,
        endDay:Int,
        sort:String = "recent"
    ): Flow<List<CalWorkDiaryEntity>>
}