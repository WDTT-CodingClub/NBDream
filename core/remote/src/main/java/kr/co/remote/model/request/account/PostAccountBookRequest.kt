package kr.co.remote.model.request.account

import kotlinx.serialization.Serializable

@Serializable
internal data class PostAccountBookRequest(
    val transactionType: String,
    val amount: Long,
    val category: String,
    val title: String,
    val registerDateTime: String,
    val imageUrls: List<String>,
    val parsedRegisterDateTime: String
)