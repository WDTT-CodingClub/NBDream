package kr.co.domain.repository

import kr.co.domain.entity.AccountBookEntity
import kr.co.domain.entity.AccountBookTotalEntity

interface AccountBookRepository {
    suspend fun getAccountBooks(
        lastContentsId: Long?,
        category: String,
        sort: String,
        start: String,
        end: String,
        transactionType: String
    ): Pair<AccountBookTotalEntity, List<AccountBookEntity>>

    suspend fun createAccountBook(
        transactionType: String,
        amount: Long,
        category: String,
        title: String,
        registerDateTime: String,
        imageUrls: List<String>
    )

    suspend fun updateAccountBook(
        id: Long,
        transactionType: String,
        amount: Long,
        category: String,
        title: String,
        registerDateTime: String,
        imageUrls: List<String>
    )

    suspend fun getAccountBookDetail(id: Long): AccountBookEntity

    suspend fun deleteAccountBook(id: Long)

}
