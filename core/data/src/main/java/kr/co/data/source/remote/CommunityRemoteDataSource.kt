package kr.co.data.source.remote

import kr.co.data.model.data.community.ApiResponseLongData
import kr.co.data.model.data.community.GetBulletinDetailData
import kr.co.data.model.data.community.GetBulletinsResult

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

    suspend fun getBulletins(
        keyword: String?,
        bulletinCategory: String,
        crop: String,
        lastBulletinId: Long?,
    ): GetBulletinsResult

    suspend fun getBulletinDetail(bulletinId: Long): GetBulletinDetailData

}
