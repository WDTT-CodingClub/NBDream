package kr.co.domain.repository

import kr.co.domain.entity.AccountBookEntity
import kr.co.domain.entity.AccountBookTotalEntity

interface AccountBookRepository {
    suspend fun getAccountBooks(
        page: Int,
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
        id: String,
        transactionType: String,
        amount: Long,
        category: String,
        title: String,
        registerDateTime: String,
        imageUrls: List<String>
    )

    suspend fun getAccountBookDetail(id: String): AccountBookEntity

    suspend fun deleteAccountBook(id: String)

}
