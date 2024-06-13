package kr.co.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import kr.co.data.model.data.AccountBookData
import kr.co.data.source.remote.AccountBookRemoteDataSource
import kr.co.remote.mapper.AccountBookListRemoteMapper
import kr.co.remote.mapper.AccountBookRemoteMapper
import kr.co.remote.model.request.account.GetAccountBookListRequest
import kr.co.remote.model.request.account.PostAccountBookRequest
import kr.co.remote.model.request.account.UpdateAccountBookRequest
import kr.co.remote.model.response.GetAccountBookDetailResponse
import kr.co.remote.model.response.GetAccountBookListResponse
import javax.inject.Inject

internal class AccountBookRemoteDataSourceImpl @Inject constructor(
    private val client: HttpClient,
): AccountBookRemoteDataSource {

    companion object {
        private const val GET_ACCOUNT_LIST = "api/auth/account"
        private const val POST_ACCOUNT = "api/auth/account"
        private const val PUT_ACCOUNT = "api/auth/account/register"
        private const val GET_ACCOUNT_DETAIL = "api/auth/account/detail"
        private const val DELETE_ACCOUNT = "api/auth/account/delete"
    }
    override suspend fun create(
        expense: Long?,
        revenue: Long?,
        category: String,
        title: String,
        registerDateTime: String,
        imageUrl: List<String>
    ) {
        PostAccountBookRequest(
            expense = expense,
            revenue = revenue,
            category = category,
            title = title,
            registerDateTime = registerDateTime,
            imageUrl = imageUrl
        ).run {
            client.post(POST_ACCOUNT)
        }
    }

    override suspend fun fetchList(
        page: Int,
        category: String,
        sort: String,
        start: String,
        end: String,
        cost: String
    ) =
        GetAccountBookListRequest(
            page = page,
            category = category,
            sort = sort,
            start = start,
            end = end,
            cost = cost
        ).run {
            client.get(GET_ACCOUNT_LIST)
                .body<GetAccountBookListResponse>()
                .let(AccountBookListRemoteMapper::convert)
        }

    override suspend fun update(
        id: String,
        expense: Long?,
        revenue: Long?,
        category: String,
        title: String,
        registerDateTime: String
    ) {
        UpdateAccountBookRequest(
            id = id,
            expense = expense,
            revenue = revenue,
            category = category,
            title = title,
            registerDateTime = registerDateTime
        ).run {
            client.put(PUT_ACCOUNT)
        }
    }

    override suspend fun fetchDetail(id: String): AccountBookData {
        return client.get(GET_ACCOUNT_DETAIL.replace("{id}", id))
            .body<GetAccountBookDetailResponse>()
            .let(AccountBookRemoteMapper::convert)
    }

    override suspend fun delete(id: String) {
        client.delete("$DELETE_ACCOUNT/$id")
    }

}