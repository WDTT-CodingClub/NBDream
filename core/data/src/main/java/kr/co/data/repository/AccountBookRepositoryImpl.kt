package kr.co.data.repository

import kr.co.data.mapper.GetAccountBookMapper
import kr.co.data.source.remote.AccountBookRemoteDataSource
import kr.co.domain.repository.AccountBookRepository
import javax.inject.Inject


internal class AccountBookRepositoryImpl @Inject constructor(
    private val remote: AccountBookRemoteDataSource
) : AccountBookRepository {
    override suspend fun getAccountBooks(
        page: Int,
        category: String,
        sort: String,
        start: String,
        end: String,
        cost: String
    ) = remote.fetchList(
        page = page,
        category = category,
        sort = sort,
        start = start,
        end = end,
        cost = cost
    ).let(GetAccountBookMapper::convert)

//    override suspend fun createAccountBook(accountBook: kr.co.domain.entity.AccountBookEntity) = Unit
//        val body = AccountBookRequest(
//            title = accountBook.title,
//            category = accountBook.category.name,
//            imageUrl = accountBook.imageUrl,
//            registerDateTime = accountBook.registerDateTime,
//            year = accountBook.year,
//            month = accountBook.month,
//            day = accountBook.day,
//            dayName = accountBook.dayName,
//            revenue = accountBook.revenue,
//            expense = accountBook.expense,
//            totalRevenue = accountBook.totalRevenue,
//            totalExpense = accountBook.totalExpense,
//            totalCost = accountBook.totalCost
//        )
//
//        return networkApi.sendRequest<kr.co.domain.entity.AccountBookEntity>(
//            url = "create_url",
//            method = RequestType.POST,
//            body = body
//        ).response
    }


//    override suspend fun updateAccountBook(
//        id: String,
//        accountBook: kr.co.domain.entity.AccountBookEntity
//    )= Unit
//        val body = AccountBookRequest(
//            id = id,
//            title = accountBook.title,
//            category = accountBook.category.name,
//            imageUrl = accountBook.imageUrl,
//            registerDateTime = accountBook.registerDateTime,
//            year = accountBook.year,
//            month = accountBook.month,
//            day = accountBook.day,
//            dayName = accountBook.dayName,
//            revenue = accountBook.revenue,
//            expense = accountBook.expense,
//            totalRevenue = accountBook.totalRevenue,
//            totalExpense = accountBook.totalExpense,
//            totalCost = accountBook.totalCost
//        )
//
//        return networkApi.sendRequest<kr.co.domain.entity.AccountBookEntity>(
//            url = "update_url",
//            method = RequestType.PUT,
//            body = body
//        ).response
//    }
//
//    override suspend fun deleteAccountBook(id: String): ApiResponse<Unit> {
//        return networkApi.sendRequest<Unit>(
//            url = "delete_url/$id",
//            method = RequestType.DELETE
//        ).response
//    }

//}

