package kr.co.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import kr.co.data.model.data.AccountBookData
import kr.co.data.model.data.AccountBookListData
import kr.co.data.source.remote.AccountBookRemoteDataSource
import kr.co.remote.mapper.AccountBookListRemoteMapper
import kr.co.remote.mapper.AccountBookRemoteMapper
import kr.co.remote.model.request.account.PostAccountBookRequest
import kr.co.remote.model.request.account.UpdateAccountBookRequest
import kr.co.remote.model.response.GetAccountBookDetailResponse
import kr.co.remote.model.response.GetAccountBookListResponse
import javax.inject.Inject

internal class AccountBookRemoteDataSourceImpl @Inject constructor(
    private val client: HttpClient,
) : AccountBookRemoteDataSource {

    companion object {
        private const val GET_ACCOUNT_LIST = "auth/account"
        private const val POST_ACCOUNT = "auth/account/register"
        private const val PUT_ACCOUNT = "auth/account/update"
        private const val GET_ACCOUNT_DETAIL = "auth/account/detail"
        private const val DELETE_ACCOUNT = "auth/account/delete"
    }

    override suspend fun create(
        transactionType: String,
        amount: Long,
        category: String,
        title: String,
        registerDateTime: String,
        imageUrls: List<String>
    ) {
        client.post(POST_ACCOUNT) {
            setBody(
                PostAccountBookRequest(
                    transactionType = transactionType,
                    amount = amount,
                    category = category,
                    title = title,
                    registerDateTime = registerDateTime,
                    imageUrls = imageUrls
                )
            )
        }
    }

    override suspend fun fetchList(
        lastContentsId: Long?,
        category: String,
        sort: String,
        start: String,
        end: String,
        transactionType: String
    ): AccountBookListData {
        return client.get(GET_ACCOUNT_LIST) {
            parameter("lastContentsId", lastContentsId)
            parameter("category", category)
            parameter("sort", sort)
            parameter("start", start)
            parameter("end", end)
            parameter("transactionType", transactionType)
        }.body<GetAccountBookListResponse>().let(AccountBookListRemoteMapper::convert)
    }


    override suspend fun update(
        id: Long,
        transactionType: String,
        amount: Long,
        category: String,
        title: String,
        registerDateTime: String,
        imageUrls: List<String>
    ) {
        client.put("$PUT_ACCOUNT/$id") {
            setBody(
                UpdateAccountBookRequest(
                    id = id,
                    transactionType = transactionType,
                    amount = amount,
                    category = category,
                    title = title,
                    registerDateTime = registerDateTime,
                    imageUrls = imageUrls
                )
            )
        }
    }

    override suspend fun fetchDetail(id: Long): AccountBookData =
        client.get("$GET_ACCOUNT_DETAIL/$id")
            .body<GetAccountBookDetailResponse>()
            .let(AccountBookRemoteMapper::convert)


    override suspend fun delete(id: Long) {
        client.delete("$DELETE_ACCOUNT/$id")
    }

}