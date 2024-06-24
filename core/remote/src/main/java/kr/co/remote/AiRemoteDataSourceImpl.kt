package kr.co.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kr.co.data.source.remote.AiRemoteDataSource
import kr.co.remote.model.Dto

internal class AiRemoteDataSourceImpl(
    private val client: HttpClient,
) : AiRemoteDataSource {

    private companion object {
        const val CHAT_URL = "/api/ai"
    }

    override suspend fun send(m: String): String {
        return client.post(CHAT_URL) {
            setBody(
                buildJsonObject {
                    put("question", m)
                }
            )
        }.body<Dto<String>>().data
    }
}
