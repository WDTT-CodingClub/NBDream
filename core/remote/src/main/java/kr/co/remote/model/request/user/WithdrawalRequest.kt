package kr.co.remote.model.request.user

import kotlinx.serialization.Serializable

@Serializable
internal data class WithdrawalRequest (
    val reason: String,
    val otherReasons: String,
)