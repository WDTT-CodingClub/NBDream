package kr.co.remote.model.response.ai

import kotlinx.serialization.Serializable

@Serializable
internal data class PostAiChatResponse(
    val answer: String,
)