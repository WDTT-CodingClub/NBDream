package kr.co.remote.mapper

import kr.co.data.model.data.AccountBookData
import kr.co.remote.model.response.GetAccountBookDetailResponse

internal object AccountBookRemoteMapper {
    fun convert(response: GetAccountBookDetailResponse): AccountBookData {
        return AccountBookData(
            id = response.id,
            title = response.title,
            category = response.category,
            year = response.year,
            month = response.month,
            day = response.day,
            dayName = response.dayName,
            revenue = response.revenue,
            expense = response.expense,
            imageUrls = response.imageUrls,
            registerDateTime = response.registerDateTime
        )
    }
}