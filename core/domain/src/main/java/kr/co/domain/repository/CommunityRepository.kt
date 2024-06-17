package kr.co.domain.repository

interface CommunityRepository {

    suspend fun postBulletin(
        content: String,
        dreamCrop: String,
        bulletinCategory: String,
        imageUrls: List<String>,
    ): Long

}
