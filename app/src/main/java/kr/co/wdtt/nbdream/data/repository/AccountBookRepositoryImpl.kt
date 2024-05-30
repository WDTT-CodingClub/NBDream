package kr.co.wdtt.nbdream.data.repository

import kotlinx.coroutines.flow.Flow
import kr.co.wdtt.nbdream.data.mapper.AccountBookMapper
import kr.co.wdtt.nbdream.data.mapper.EntityWrapper
import kr.co.wdtt.nbdream.data.remote.dto.AccountBookRequest
import kr.co.wdtt.nbdream.data.remote.dto.AccountBookResponse
import kr.co.wdtt.nbdream.data.remote.model.ApiResponse
import kr.co.wdtt.nbdream.data.remote.model.RequestType
import kr.co.wdtt.nbdream.data.remote.retrofit.NetworkFactoryManager
import kr.co.wdtt.nbdream.domain.entity.AccountBookEntity
import kr.co.wdtt.nbdream.domain.repository.AccountBookRepository
import javax.inject.Inject


internal class AccountBookRepositoryImpl @Inject constructor(
    network: NetworkFactoryManager,
    private val mapper: AccountBookMapper
) : AccountBookRepository {
    companion object {
        const val BASE_URL = "base_url"
        const val HEAD_AUTH = "head_auth"
        const val HEAD_KEY = "head_key"
    }

    private val queryMap = mutableMapOf<String, String>()

    private val networkApi = network.create(
        BASE_URL,
        HEAD_AUTH,
        HEAD_KEY
    )

    override suspend fun getAccountBooks(
        userId: String,
        startDate: String,
        endDate: String,
        type: String,
        sortOrder: String,
        category: AccountBookEntity.Category
    ): Flow<EntityWrapper<List<AccountBookEntity>>> =
        mapper.mapFromResult(
            networkApi.sendRequest<AccountBookResponse>(
                url = "get_url",
                method = RequestType.GET,
                queryMap = queryMap.apply {
                    clear()
                    put("userId", userId)
                    put("startDate", startDate)
                    put("endDate", endDate)
                    put("type", type)
                    put("sortOrder", sortOrder)
                    put("category", category.name)
                }
            )
        )

    override suspend fun createAccountBook(accountBook: AccountBookEntity): ApiResponse<AccountBookEntity> {
        val body = AccountBookRequest(
            title = accountBook.title,
            category = accountBook.category.name,
            imageUrl = accountBook.imageUrl,
            registerDateTime = accountBook.registerDateTime,
            year = accountBook.year,
            month = accountBook.month,
            day = accountBook.day,
            dayName = accountBook.dayName,
            revenue = accountBook.revenue,
            expense = accountBook.expense,
            totalRevenue = accountBook.totalRevenue,
            totalExpense = accountBook.totalExpense,
            totalCost = accountBook.totalCost
        )

        return networkApi.sendRequest<AccountBookEntity>(
            url = "create_url",
            method = RequestType.POST,
            body = body
        ).response
    }


    override suspend fun updateAccountBook(
        id: String,
        accountBook: AccountBookEntity
    ): ApiResponse<AccountBookEntity> {
        val body = AccountBookRequest(
            id = id,
            title = accountBook.title,
            category = accountBook.category.name,
            imageUrl = accountBook.imageUrl,
            registerDateTime = accountBook.registerDateTime,
            year = accountBook.year,
            month = accountBook.month,
            day = accountBook.day,
            dayName = accountBook.dayName,
            revenue = accountBook.revenue,
            expense = accountBook.expense,
            totalRevenue = accountBook.totalRevenue,
            totalExpense = accountBook.totalExpense,
            totalCost = accountBook.totalCost
        )

        return networkApi.sendRequest<AccountBookEntity>(
            url = "update_url",
            method = RequestType.PUT,
            body = body
        ).response
    }

    override suspend fun deleteAccountBook(id: String): ApiResponse<Unit> {
        return networkApi.sendRequest<Unit>(
            url = "delete_url/$id",
            method = RequestType.DELETE
        ).response
    }

}

