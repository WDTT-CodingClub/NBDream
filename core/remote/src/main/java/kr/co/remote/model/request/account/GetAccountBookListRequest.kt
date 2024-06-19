package kr.co.remote.model.request.account

import kotlinx.serialization.Serializable

@Serializable
internal data class GetAccountBookListRequest(
    val lastContentsId: Int,
    val category: String,
    val sort: String,
    val start: String,
    val end: String,
    val transactionType: String,
    val categoryEnum: String,
    val transactionTypeEnum: String,
    val sortEnum: String
)