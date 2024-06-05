package kr.co.remote.mapper

import kr.co.common.mapper.Mapper
import kr.co.data.model.data.AccountBookListResult
import kr.co.remote.model.response.GetAccountBookListResponse

internal object AccountBookListRemoteMapper
    : Mapper<GetAccountBookListResponse, AccountBookListResult> {
    override fun convert(param: GetAccountBookListResponse): AccountBookListResult =
        with(param.resultData) {
            AccountBookListResult(
                categories = categories,
                totalRevenue = totalRevenue,
                totalExpense = totalExpense,
                totalCost = totalCost,
                items = items.map {
                    AccountBookListResult.Item(
                        id = it.id,
                        title = it.title,
                        category = it.category,
                        year = it.year,
                        month = it.month,
                        day = it.day,
                        dayName = it.dayName,
                        revenue = it.revenue,
                        expense = it.expense,
                        thumbnail = it.thumbnail,
                        imageSize = it.imageSize

                    )
                }
            )
        }

}