package kr.co.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import kr.co.data.source.remote.CommunityRemoteDataSource
import kr.co.remote.model.request.community.BulletinReqDto
import kr.co.remote.model.response.ApiResponseLong
import kr.co.remote.model.response.community.ApiResponseBulletinsResDto
import kr.co.remote.model.response.community.ApiResponseVoid
import kr.co.remote.model.response.community.GetBulletinDetailResponse
import kr.co.remote.model.response.community.convertToData
import kr.co.remote.model.response.convertToData
import javax.inject.Inject

internal class CommunityRemoteDataSourceImpl @Inject constructor(
    private val client: HttpClient
) : CommunityRemoteDataSource {
    companion object {
        private const val POST_BULLETIN_URL = "api/bulletins"
        private const val DELETE_BULLETIN_URL = "api/bulletins/"
        private const val PUT_BULLETIN_URL = DELETE_BULLETIN_URL
        private const val GET_BULLETINS_URL = "api/bulletins"
        private const val GET_BOOKMARKS_URL = "api/bulletins/bookmarks"
        private const val GET_MY_BULLETINS_URL = "api/bulletins/my-bulletins"
        private const val GET_BULLETIN_DETAIL_URL = "api/bulletins/"
    }

    override suspend fun postBulletin(
        content: String,
        crop: String,
        bulletinCategory: String,
        imageUrls: List<String>,
    ) = client.post(POST_BULLETIN_URL) {
        setBody(
            BulletinReqDto(
                content = content,
                dreamCrop = crop,
                bulletinCategory = bulletinCategory,
                imageUrls = imageUrls,
            )
        )
    }.body<ApiResponseLong>().convertToData()

    override suspend fun deleteBulletin(
        id: Long,
    ) = client.delete(DELETE_BULLETIN_URL + id) {
    }.body<ApiResponseVoid>().convertToData()

    override suspend fun putBulletin(
        id: Long,
        content: String,
        crop: String,
        bulletinCategory: String,
        imageUrls: List<String>,
    ) = client.put(PUT_BULLETIN_URL + id) {
        setBody(
            BulletinReqDto(
                content = content,
                dreamCrop = crop,
                bulletinCategory = bulletinCategory,
                imageUrls = imageUrls,
            )
        )
    }.body<ApiResponseLong>().convertToData()

    override suspend fun getBulletins(
        keyword: String?,
        bulletinCategory: String,
        crop: String,
        lastBulletinId: Long?,
    ) = client.get(GET_BULLETINS_URL) {
        url {
            keyword?.let { parameters.append("keyword", it) }
            parameters.append("bulletinCategory", bulletinCategory)
            parameters.append("crop", crop)
            lastBulletinId?.let { parameters.append("lastBulletinId", it.toString()) }
        }
    }.body<ApiResponseBulletinsResDto>().convertToData()

    override suspend fun getMyBulletins(
        lastBulletinId: Long?,
    ) = client.get(GET_MY_BULLETINS_URL) {
        url {
            parameters.append("lastBulletinId", lastBulletinId.toString())
        }
    }.body<ApiResponseBulletinsResDto>().convertToData()

    override suspend fun getBookmarks(
        lastBulletinId: Long?,
    ) = client.get(GET_BOOKMARKS_URL) {
        url {
            parameters.append("lastBulletinId", lastBulletinId.toString())
        }
    }.body<ApiResponseBulletinsResDto>().convertToData()

    override suspend fun getBulletinDetail(
        bulletinId: Long,
    ) = client.get(GET_BULLETIN_DETAIL_URL + bulletinId)
        .body<GetBulletinDetailResponse>().convertToData()

}
