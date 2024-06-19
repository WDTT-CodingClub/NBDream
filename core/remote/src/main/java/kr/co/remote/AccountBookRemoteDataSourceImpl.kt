package kr.co.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
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
) : AccountBookRemoteDataSource {

    companion object {
        private const val GET_ACCOUNT_LIST = "auth/account"
        private const val POST_ACCOUNT = "auth/account"
        private const val PUT_ACCOUNT = "auth/account/register"
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
                    imageUrls = imageUrls,
                    parsedRegisterDateTime = registerDateTime
                )
            )
        }
    }

    override suspend fun fetchList(
        page: Int,
        category: String,
        sort: String,
        start: String,
        end: String,
        transactionType: String
    ) = client.get(GET_ACCOUNT_LIST) {
        setBody(
            GetAccountBookListRequest(
                lastContentsId = page,
                category = category,
                sort = sort,
                start = start,
                end = end,
                transactionType = transactionType,
                categoryEnum = category,
                transactionTypeEnum = transactionType,
                sortEnum = sort
            )
        )
    }
        .body<GetAccountBookListResponse>()
        .let(AccountBookListRemoteMapper::convert)


    override suspend fun update(
        id: String,
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
                    imageUrls = imageUrls,
                    parsedRegisterDateTime = registerDateTime
                )
            )
        }
    }

    override suspend fun fetchDetail(id: String): AccountBookData =
         client.get("$GET_ACCOUNT_DETAIL/$id")
            .body<GetAccountBookDetailResponse>()
            .let(AccountBookRemoteMapper::convert)


    override suspend fun delete(id: String) {
        client.delete("$DELETE_ACCOUNT/$id")
    }

}