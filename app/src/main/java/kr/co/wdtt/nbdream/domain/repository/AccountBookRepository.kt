package kr.co.wdtt.nbdream.domain.repository

import kotlinx.coroutines.flow.Flow
import kr.co.wdtt.nbdream.domain.entity.AccountBookEntity
import kr.co.wdtt.nbdream.domain.entity.Category

interface AccountBookRepository {
    suspend fun getAccountBooks(
        userId: String,
        startDate: String,
        endDate: String,
        type: String, // "all", "revenue", "expense"
        sortOrder: String, // "newest", "oldest"
        category: Category
    ): Flow<List<AccountBookEntity>>

    suspend fun createAccountBook(accountBook: AccountBookEntity): AccountBookEntity

    suspend fun updateAccountBook(id: String, accountBook: AccountBookEntity): AccountBookEntity

    suspend fun deleteAccountBook(id: String)
}
