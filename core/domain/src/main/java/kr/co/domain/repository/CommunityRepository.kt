package kr.co.domain.repository

import kr.co.domain.entity.BulletinEntity
import kr.co.domain.entity.CropEntity

interface CommunityRepository {

    suspend fun postBulletin(
        content: String,
        dreamCrop: String,
        bulletinCategory: String,
        imageUrls: List<String>,
    ): Long

    suspend fun getBulletins(
        keyword: String?,
        bulletinCategory: BulletinEntity.BulletinCategory,
        crop: CropEntity.Name,
        lastBulletinId: Long?,
    ): List<BulletinEntity>

}
