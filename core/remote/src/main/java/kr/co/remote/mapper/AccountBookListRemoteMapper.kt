package kr.co.remote.mapper

import kr.co.common.mapper.Mapper
import kr.co.data.model.data.AccountBookListData
import kr.co.remote.model.response.GetAccountBookListResponse

internal object AccountBookListRemoteMapper : Mapper<GetAccountBookListResponse, AccountBookListData> {
    override fun convert(param: GetAccountBookListResponse): AccountBookListData =
        with(param.data) {
            AccountBookListData(
                categories = categories,
                totalRevenue = totalRevenue,
                totalExpense = totalExpense,
                totalCost = totalCost,
                items = items.map {
                    AccountBookListData.Item(
                        id = it.id,
                        title = it.title,
                        category = it.category,
                        year = it.year,
                        month = it.month,
                        day = it.day,
                        dayName = it.dayName,
                        transactionType = it.transactionType,
                        amount = it.amount ?: 0L,
                        thumbnail = it.thumbnail,
                        imageSize = it.imageSize
                    )
                }
            )
        }
}
