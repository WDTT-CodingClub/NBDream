package kr.co.wdtt.nbdream.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.co.wdtt.nbdream.data.mapper.AccountBookMapper
import kr.co.wdtt.nbdream.data.mapper.EntityWrapper
import kr.co.wdtt.nbdream.data.remote.dto.AccountBookResponse
import kr.co.wdtt.nbdream.data.remote.model.ApiResponse
import kr.co.wdtt.nbdream.data.remote.model.RequestType
import kr.co.wdtt.nbdream.data.remote.retrofit.NetworkFactoryManager
import kr.co.wdtt.nbdream.domain.entity.AccountBookEntity
import kr.co.wdtt.nbdream.domain.entity.Category
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
        category: Category
    ): Flow<List<AccountBookEntity>> {
        queryMap.clear()
        queryMap.putAll(
            mapOf(
                "userId" to userId,
                "startDate" to startDate,
                "endDate" to endDate,
                "type" to type,
                "sortOrder" to sortOrder,
                "category" to category.name
            )
        )
        val apiResult = networkApi.sendRequest<AccountBookResponse>(
            url = "get_url",
            method = RequestType.GET,
            queryMap = queryMap
        )
        return mapper.mapFromResult(apiResult).map {
            when (it) {
                is EntityWrapper.Success -> it.data
                is EntityWrapper.Fail -> throw IllegalStateException("Get account book failed: ${it.throwable?.message}")
            }
        }
    }

    override suspend fun createAccountBook(accountBook: AccountBookEntity): AccountBookEntity {
        val body = mapOf(
            "title" to accountBook.title,
            "category" to accountBook.category.name,
            "imageUrl" to accountBook.imageUrl.joinToString(separator = ","),
            "registerDateTime" to (accountBook.registerDateTime ?: ""),
            "year" to (accountBook.year?.toString() ?: ""),
            "month" to (accountBook.month?.toString() ?: ""),
            "day" to (accountBook.day?.toString() ?: ""),
            "dayName" to (accountBook.dayName ?: ""),
            "revenue" to (accountBook.revenue?.toString() ?: ""),
            "expense" to (accountBook.expense?.toString() ?: ""),
            "totalRevenue" to (accountBook.totalRevenue?.toString() ?: ""),
            "totalExpense" to (accountBook.totalExpense?.toString() ?: ""),
            "totalCost" to (accountBook.totalCost?.toString() ?: "")
        )

        val apiResult = networkApi.sendRequest<AccountBookEntity>(
            url = "create_url",
            method = RequestType.POST,
            body = body
        )
        return when (val response = apiResult.response) {
            is ApiResponse.Success -> response.data
            is ApiResponse.Fail -> throw IllegalStateException("Create account book failed: ${response.error.message}")
        }
    }

    override suspend fun updateAccountBook(
        id: String,
        accountBook: AccountBookEntity
    ): AccountBookEntity {
        val body = mapOf(
            "id" to id,
            "title" to accountBook.title,
            "category" to accountBook.category.name,
            "imageUrl" to accountBook.imageUrl.joinToString(separator = ","),
            "registerDateTime" to (accountBook.registerDateTime ?: ""),
            "year" to (accountBook.year?.toString() ?: ""),
            "month" to (accountBook.month?.toString() ?: ""),
            "day" to (accountBook.day?.toString() ?: ""),
            "dayName" to (accountBook.dayName ?: ""),
            "revenue" to (accountBook.revenue?.toString() ?: ""),
            "expense" to (accountBook.expense?.toString() ?: ""),
            "totalRevenue" to (accountBook.totalRevenue?.toString() ?: ""),
            "totalExpense" to (accountBook.totalExpense?.toString() ?: ""),
            "totalCost" to (accountBook.totalCost?.toString() ?: "")
        )

        val apiResult = networkApi.sendRequest<AccountBookEntity>(
            url = "update_url",
            method = RequestType.PUT,
            body = body
        )
        return when (val response = apiResult.response) {
            is ApiResponse.Success -> response.data
            is ApiResponse.Fail -> throw IllegalStateException("Update account book failed: ${response.error.message}")
        }
    }

    override suspend fun deleteAccountBook(id: String) {
        val body = mapOf("id" to id)

        val apiResult = networkApi.sendRequest<Unit>(
            url = "delete_url",
            method = RequestType.DELETE,
            body = body
        )
        if (apiResult.response is ApiResponse.Fail) {
            throw IllegalStateException("Delete account book failed: ${apiResult.response.error.message}")
        }
    }

}