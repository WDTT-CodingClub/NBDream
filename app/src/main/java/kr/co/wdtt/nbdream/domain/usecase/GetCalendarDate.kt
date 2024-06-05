package kr.co.wdtt.nbdream.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.Flow
import kr.co.wdtt.nbdream.domain.entity.CalendarDateEntity
import kr.co.wdtt.nbdream.domain.entity.DreamCrop

interface GetCalendarDate {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend operator fun invoke(crop: DreamCrop, year: Int, month: Int): Flow<List<CalendarDateEntity>>
}