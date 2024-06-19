package kr.co.data.source.remote

import kr.co.data.model.data.AccountBookData
import kr.co.data.model.data.AccountBookListData

interface AccountBookRemoteDataSource {
    suspend fun fetchList(
        page: Int,
        category: String,
        sort: String,
        start: String,
        end: String,
        transactionType: String
    ): AccountBookListData

    suspend fun create(
        transactionType: String,
        amount: Long,
        category: String,
        title: String,
        registerDateTime: String,
        imageUrls: List<String>
    )

    suspend fun update(
        id: String,
        transactionType: String,
        amount: Long,
        category: String,
        title: String,
        registerDateTime: String,
        imageUrls: List<String>
    )

    suspend fun fetchDetail(id: String): AccountBookData

    suspend fun delete(id: String)
}