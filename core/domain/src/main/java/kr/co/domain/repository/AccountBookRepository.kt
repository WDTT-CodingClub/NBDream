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
        cost: String
    ): Pair<AccountBookTotalEntity, List<AccountBookEntity>>

    suspend fun createAccountBook(
        expense: Long?,
        revenue: Long?,
        category: String,
        title: String,
        registerDateTime: String
    )

    suspend fun updateAccountBook(
        id: String,
        expense: Long?,
        revenue: Long?,
        category: String,
        title: String,
        registerDateTime: String
    )

    suspend fun getAccountBookDetail(id: String): AccountBookEntity

    suspend fun deleteAccountBook(id: String)

//    suspend fun createAccountBook(accountBook: AccountBookEntity): kr.co.data.model.ApiResponse<AccountBookEntity>
//
//    suspend fun updateAccountBook(id: String, accountBook: AccountBookEntity): kr.co.data.model.ApiResponse<AccountBookEntity>
//
//    suspend fun deleteAccountBook(id: String): kr.co.data.model.ApiResponse<Unit>
}
