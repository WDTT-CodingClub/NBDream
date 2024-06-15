package kr.co.remote.model.request.account

import kotlinx.serialization.Serializable

@Serializable
internal data class PostAccountBookRequest(
    val title: String,
    val category: String,
    val imageUrl: List<String>,
    val revenue: Long? = null,
    val expense: Long? = null,
    val registerDateTime: String,
)