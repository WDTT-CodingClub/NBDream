package kr.co.data.source.remote

import kr.co.data.model.data.AccountBookData
import kr.co.data.model.data.AccountBookListData

interface AccountBookRemoteDataSource {
    suspend fun create(
        expense: Long? = null,
        revenue: Long? = null,
        category: String,
        title: String,
        registerDateTime: String,
        imageUrl: List<String> = emptyList()
    )

    suspend fun fetchList(
        page: Int,
        category: String,
        sort: String,
        start: String,
        end: String,
        cost: String
    ): AccountBookListData

    suspend fun update(
        id: String,
        expense: Long? = null,
        revenue: Long? = null,
        category: String,
        title: String,
        registerDateTime: String
    )

    suspend fun fetchDetail(id: String): AccountBookData

    suspend fun delete(id: String)
}
