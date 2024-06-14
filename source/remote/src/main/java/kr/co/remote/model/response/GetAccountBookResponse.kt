package kr.co.remote.model.response

import kotlinx.serialization.Serializable

@Serializable
internal data class GetAccountBookDetailResponse(
    val id: String,
    val title: String,
    val category: String,
    val year: Int,
    val month: Int,
    val day: Int,
    val dayName: String,
    val revenue: Long?,
    val expense: Long?,
    val imageUrls: List<String>,
    val registerDateTime: String
)