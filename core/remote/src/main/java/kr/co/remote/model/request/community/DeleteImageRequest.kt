package kr.co.remote.model.request.community

import kotlinx.serialization.Serializable

@Serializable
internal data class DeleteImageRequest(
    val imageUrl: String,
)
