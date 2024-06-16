package kr.co.remote.model.request.account

import kotlinx.serialization.Serializable

@Serializable
internal data class UpdateAccountBookRequest(
    val id: String,
    val expense: Long?,
    val revenue: Long?,
    val category: String,
    val title: String,
    val registerDateTime: String
)