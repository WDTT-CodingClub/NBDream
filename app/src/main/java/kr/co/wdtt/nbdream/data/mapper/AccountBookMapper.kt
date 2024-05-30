package kr.co.wdtt.nbdream.data.mapper

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kr.co.wdtt.core.domain.base.BaseMapper
import kr.co.wdtt.nbdream.data.remote.dto.AccountBookResponse
import kr.co.wdtt.nbdream.domain.entity.AccountBookEntity

class AccountBookMapper : BaseMapper<AccountBookResponse, List<AccountBookEntity>>() {
    override fun getSuccess(data: AccountBookResponse?, extra: Any?): Flow<EntityWrapper.Success<List<AccountBookEntity>>> {
        return flow {
            data?.let { response ->
                val accountBooks = response.response.body.items.map { item ->
                    val category = AccountBookEntity.Category.entries.find { it.value == item.category }
                        ?: AccountBookEntity.Category.OTHER

                    AccountBookEntity(
                        id = item.id,
                        title = item.title,
                        category = category,
                        imageUrl = item.imageUrl,
                        registerDateTime = item.registerDateTime,
                        year = item.year,
                        month = item.month,
                        day = item.day,
                        dayName = item.dayName,
                        revenue = item.revenue,
                        expense = item.expense,
                        totalRevenue = item.totalRevenue,
                        totalExpense = item.totalExpense,
                        totalCost = item.totalCost
                    )
                }
                emit(EntityWrapper.Success(accountBooks))
            } ?: emit(EntityWrapper.Success(emptyList()))
        }
    }

    override fun getFailure(error: Throwable): Flow<EntityWrapper.Fail<List<AccountBookEntity>>> {
        return flow { emit(EntityWrapper.Fail(error)) }
    }
}
