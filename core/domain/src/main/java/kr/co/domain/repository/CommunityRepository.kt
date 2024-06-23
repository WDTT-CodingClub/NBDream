package kr.co.domain.repository

import kr.co.domain.entity.BulletinEntity
import kr.co.domain.entity.CropEntity

interface CommunityRepository {

    suspend fun postBulletin(
        content: String,
        crop: CropEntity.Name,
        bulletinCategory: BulletinEntity.BulletinCategory,
        imageUrls: List<String>,
    ): Long

    suspend fun deleteBulletin(id: Long): Boolean

    suspend fun putBulletin(
        id: Long,
        content: String,
        crop: CropEntity.Name,
        bulletinCategory: BulletinEntity.BulletinCategory,
        imageUrls: List<String>
    ): Long

    suspend fun bookmarkBulletin(id: Long): Boolean

    suspend fun getBulletins(
        keyword: String?,
        bulletinCategory: BulletinEntity.BulletinCategory,
        crop: CropEntity.Name,
        lastBulletinId: Long?,
    ): List<BulletinEntity>

    suspend fun getMyBulletins(lastBulletinId: Long? = null): List<BulletinEntity>

    suspend fun getBookmarks(lastBulletinId: Long? = null): List<BulletinEntity>

    suspend fun getBulletinDetail(bulletinId: Long): BulletinEntity?

}
