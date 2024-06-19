package kr.co.data.mapper

import kr.co.common.mapper.Mapper
import kr.co.data.model.data.AccountBookListData
import kr.co.domain.entity.AccountBookEntity
import kr.co.domain.entity.AccountBookTotalEntity

internal object GetAccountBookListMapper
    :Mapper<AccountBookListData, Pair<AccountBookTotalEntity, List<AccountBookEntity>>> {
    override fun convert(param: AccountBookListData) =
        with(param) {
            AccountBookTotalEntity(
                totalCost = totalCost,
                totalExpense = totalExpense,
                totalRevenue = totalRevenue,
                categories = categories
            ) to items.map {
                AccountBookEntity(
                    id = it.id,
                    title = it.title,
                    category = toCategory(it.category),
                    imageUrl = listOf(it.thumbnail),
                    year = it.year,
                    month = it.month,
                    day = it.day,
                    dayName = it.dayName,
                    transactionType = toTransactionType(it.transactionType),
                    amount = it.amount
                )
            }
        }

    fun toCategory(category: String) =
        AccountBookEntity.Category.entries.find {
            it.display == category
        }?: AccountBookEntity.Category.OTHER

    fun toTransactionType(transactionType: String) =
        AccountBookEntity.TransactionType.entries.find {
            it.value == transactionType
        }
}
