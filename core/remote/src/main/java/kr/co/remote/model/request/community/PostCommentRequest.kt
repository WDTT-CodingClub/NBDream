package kr.co.remote.model.request.community

import kotlinx.serialization.Serializable

@Serializable
internal data class PostCommentRequest(
    val commentDetail: String,
)
