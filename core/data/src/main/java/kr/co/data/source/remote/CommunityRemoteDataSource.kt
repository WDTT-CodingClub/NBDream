package kr.co.data.source.remote

import kr.co.data.model.data.ApiResponseStringData
import kr.co.data.model.data.community.ApiResponseBulletinsResDtoData
import kr.co.data.model.data.community.ApiResponseListCommentResDtoData
import kr.co.data.model.data.community.ApiResponseLongData
import kr.co.data.model.data.community.GetBulletinDetailData

interface CommunityRemoteDataSource {

    suspend fun postBulletin(
        content: String,
        crop: String,
        bulletinCategory: String,
        imageUrls: List<String>,
    ): ApiResponseLongData

    suspend fun deleteBulletin(id: Long): Boolean

    suspend fun putBulletin(
        id: Long,
        content: String,
        crop: String,
        bulletinCategory: String,
        imageUrls: List<String>,
    ): ApiResponseLongData

    suspend fun bookmarkBulletin(id: Long): Boolean

    suspend fun getBulletins(
        keyword: String?,
        bulletinCategory: String,
        crop: String,
        lastBulletinId: Long?,
    ): ApiResponseBulletinsResDtoData

    suspend fun getMyBulletins(lastBulletinId: Long? = null): ApiResponseBulletinsResDtoData

    suspend fun getBookmarks(lastBulletinId: Long? = null): ApiResponseBulletinsResDtoData

    suspend fun getBulletinDetail(bulletinId: Long): GetBulletinDetailData

    suspend fun postComment(
        id: Long,
        commentDetail: String,
    ): ApiResponseLongData

    suspend fun deleteComment(id: Long): ApiResponseStringData

    suspend fun patchComment(
        id: Long,
        commentDetail: String,
    ): ApiResponseStringData

    suspend fun getMyComments(): ApiResponseListCommentResDtoData

}
