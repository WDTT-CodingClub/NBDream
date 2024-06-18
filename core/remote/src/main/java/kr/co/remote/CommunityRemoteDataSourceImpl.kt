package kr.co.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kr.co.data.source.remote.CommunityRemoteDataSource
import kr.co.remote.model.request.community.PostBulletinRequest
import kr.co.remote.model.response.community.PostBulletinResponse
import kr.co.remote.model.response.community.convertToResult
import javax.inject.Inject

internal class CommunityRemoteDataSourceImpl @Inject constructor(
    private val client: HttpClient
) : CommunityRemoteDataSource {
    companion object {
        private const val POST_BULLETIN_URL = "api/bulletins"
        private const val DELETE_BULLETIN_URL = "api/bulletins/{bulletin-id}"
        private const val PUT_BULLETIN_URL = "api/bulletins/{bulletin-id}"
    }

    override suspend fun postBulletin(
        content: String,
        dreamCrop: String,
        bulletinCategory: String,
        imageUrls: List<String>,
    ) = client.post(POST_BULLETIN_URL) {
        setBody(
            PostBulletinRequest(
                content = content,
                dreamCrop = dreamCrop,
                bulletinCategory = bulletinCategory,
                imageUrls = imageUrls,
            )
        )
    }.body<PostBulletinResponse>().convertToResult()

}
