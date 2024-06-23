package kr.co.data.repository

import kr.co.data.mapper.GetAccountBookListMapper
import kr.co.data.mapper.GetAccountBookMapper
import kr.co.data.source.remote.AccountBookRemoteDataSource
import kr.co.domain.entity.AccountBookEntity
import kr.co.domain.repository.AccountBookRepository
import javax.inject.Inject


internal class AccountBookRepositoryImpl @Inject constructor(
    private val remote: AccountBookRemoteDataSource
) : AccountBookRepository {
    override suspend fun getAccountBooks(
        lastContentsId: Long?,
        category: String,
        sort: String,
        start: String,
        end: String,
        transactionType: String
    ) = remote.fetchList(
        lastContentsId = lastContentsId,
        category = category,
        sort = sort,
        start = start,
        end = end,
        transactionType = transactionType
    ).let(GetAccountBookListMapper::convert)

    override suspend fun createAccountBook(
        transactionType: String,
        amount: Long,
        category: String,
        title: String,
        registerDateTime: String,
        imageUrls: List<String>
    ) {
        remote.create(
            transactionType = transactionType,
            amount = amount,
            category = category,
            title = title,
            registerDateTime = registerDateTime,
            imageUrls = imageUrls
        )
    }

    override suspend fun updateAccountBook(
        id: Long,
        transactionType: String,
        amount: Long,
        category: String,
        title: String,
        registerDateTime: String,
        imageUrls: List<String>
    ) {
        remote.update(
            id = id,
            transactionType = transactionType,
            amount = amount,
            category = category,
            title = title,
            registerDateTime = registerDateTime,
            imageUrls = imageUrls
        )
    }

    override suspend fun getAccountBookDetail(id: Long): AccountBookEntity {
        return remote.fetchDetail(id).let(GetAccountBookMapper::convert)
    }

    override suspend fun deleteAccountBook(id: Long) {
        remote.delete(id)
    }
}