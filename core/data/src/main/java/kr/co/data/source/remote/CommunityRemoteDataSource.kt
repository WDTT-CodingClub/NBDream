package kr.co.data.source.remote

import kr.co.data.model.data.community.GetBulletinDetailData
import kr.co.data.model.data.community.GetBulletinsResult
import kr.co.data.model.data.community.PostBulletinResult

interface CommunityRemoteDataSource {

    suspend fun postBulletin(
        content: String,
        crop: String,
        bulletinCategory: String,
        imageUrls: List<String>,
    ): PostBulletinResult

    suspend fun getBulletins(
        keyword: String?,
        bulletinCategory: String,
        crop: String,
        lastBulletinId: Long?,
    ): GetBulletinsResult

    suspend fun getBulletinDetail(bulletinId: Long): GetBulletinDetailData

}
