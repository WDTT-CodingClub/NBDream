package kr.co.wdtt.nbdream.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.co.wdtt.nbdream.data.mapper.EntityWrapper
import kr.co.wdtt.nbdream.data.remote.model.ApiResponse
import kr.co.wdtt.nbdream.domain.entity.AccountBookEntity

interface AccountBookRepository {
    suspend fun getAccountBooks(
        userId: String,
        startDate: String,
        endDate: String,
        type: String, // "all", "revenue", "expense"
        sortOrder: String, // "newest", "oldest"
        category: AccountBookEntity.Category
    ): Flow<EntityWrapper<List<AccountBookEntity>>>

    suspend fun createAccountBook(accountBook: AccountBookEntity): ApiResponse<AccountBookEntity>

    suspend fun updateAccountBook(id: String, accountBook: AccountBookEntity): ApiResponse<AccountBookEntity>

    suspend fun deleteAccountBook(id: String): ApiResponse<Unit>
}
