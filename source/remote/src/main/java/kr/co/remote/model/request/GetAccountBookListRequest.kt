package kr.co.remote.model.request

import kotlinx.serialization.Serializable

@Serializable
internal data class GetAccountBookListRequest(
    val category: String,
    val sort: String,
    val start: String,
    val end: String,
    val cost: String,
    val page: Int,
)