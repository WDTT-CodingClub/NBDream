package kr.co.domain.repository

import kr.co.domain.entity.BulletinEntity
import kr.co.domain.entity.CropEntity
import kr.co.domain.entity.type.CropType

interface CommunityRepository {

    suspend fun postBulletin(
        content: String,
        crop: CropType,
        bulletinCategory: BulletinEntity.BulletinCategory,
        imageUrls: List<String>,
    ): Long

    suspend fun getBulletins(
        keyword: String?,
        bulletinCategory: BulletinEntity.BulletinCategory,
        crop: CropType,
        lastBulletinId: Long?,
    ): List<BulletinEntity>

    suspend fun getBulletinDetail(bulletinId: Long): BulletinEntity?

}
