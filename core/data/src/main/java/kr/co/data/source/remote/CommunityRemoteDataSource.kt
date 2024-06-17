package kr.co.data.source.remote

import kr.co.data.model.data.community.PostBulletinResult

interface CommunityRemoteDataSource {

    suspend fun postBulletin(
        content: String,
        dreamCrop: String,
        bulletinCategory: String,
        imageUrls: List<String>,
    ): PostBulletinResult

}
