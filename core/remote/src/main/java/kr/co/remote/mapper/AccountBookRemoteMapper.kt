package kr.co.remote.mapper

import kr.co.data.model.data.AccountBookData
import kr.co.remote.model.response.GetAccountBookDetailResponse

internal object AccountBookRemoteMapper {
    fun convert(response: GetAccountBookDetailResponse): AccountBookData {
        return AccountBookData(
            id = response.data.id,
            title = response.data.title,
            category = response.data.category,
            transactionType = response.data.transactionType,
            amount = response.data.amount,
            imageUrls = response.data.imageUrls,
            registerDateTime = response.data.registerDateTime
        )
    }
}
