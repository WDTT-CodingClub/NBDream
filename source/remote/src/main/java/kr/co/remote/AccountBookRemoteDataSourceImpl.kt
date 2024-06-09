package kr.co.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import kr.co.data.source.remote.AccountBookRemoteDataSource
import kr.co.remote.mapper.AccountBookListRemoteMapper
import kr.co.remote.model.request.GetAccountBookListRequest
import kr.co.remote.model.request.PostAccountBookRequest
import kr.co.remote.model.response.GetAccountBookListResponse
import javax.inject.Inject

internal class AccountBookRemoteDataSourceImpl @Inject constructor(
    private val client: HttpClient,
): AccountBookRemoteDataSource {

    companion object {
        private const val GET_ACCOUNT_LIST = "auth/account"
        private const val POST_ACCOUNT = "auth/account"
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

}