package kr.co.data.source.remote

import kr.co.data.model.data.AccountBookData
import kr.co.data.model.data.AccountBookListData

interface AccountBookRemoteDataSource {
    suspend fun fetchList(
        lastContentsId: Long?,
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
        id: Long,
        transactionType: String,
        amount: Long,
        category: String,
        title: String,
        registerDateTime: String,
        imageUrls: List<String>
    )

    suspend fun fetchDetail(id: Long): AccountBookData

    suspend fun delete(id: Long)
}