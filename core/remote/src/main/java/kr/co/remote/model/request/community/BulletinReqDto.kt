package kr.co.remote.model.request.community

import kotlinx.serialization.Serializable

@Serializable
internal data class BulletinReqDto(
    val content: String,
    val dreamCrop: String,
    val bulletinCategory: String,
    val imageUrls: List<String>,
)
