package kr.co.data.mapper

import kr.co.common.mapper.Mapper
import kr.co.data.model.data.AccountBookData
import kr.co.domain.entity.AccountBookEntity

internal object GetAccountBookMapper
    : Mapper<AccountBookData, AccountBookEntity> {
    override fun convert(param: AccountBookData) =
        with(param) {
            AccountBookEntity(
                id = id,
                title = title,
                category = GetAccountBookListMapper.toCategory(category),
                imageUrl = imageUrls,
                registerDateTime = registerDateTime,
                year = year,
                month = month,
                day = day,
                dayName = dayName,
                transactionType = GetAccountBookListMapper.toTransactionType(transactionType),
                amount = amount
            )
        }
}