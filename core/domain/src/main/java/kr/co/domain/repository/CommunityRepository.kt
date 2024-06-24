package kr.co.domain.repository

import kr.co.domain.entity.BulletinEntity
import kr.co.domain.entity.CommentEntity
import kr.co.domain.entity.CropEntity
import kr.co.domain.entity.type.CropType

interface CommunityRepository {

    suspend fun postBulletin(
        content: String,
        crop: CropType,
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
        crop: CropType,
        lastBulletinId: Long?,
    ): List<BulletinEntity>

    suspend fun getMyBulletins(lastBulletinId: Long? = null): List<BulletinEntity>

    suspend fun getBookmarks(lastBulletinId: Long? = null): List<BulletinEntity>

    suspend fun getBulletinDetail(bulletinId: Long): BulletinEntity?

    suspend fun postComment(
        id: Long,
        commentDetail: String,
    ): Long

    suspend fun deleteComment(id: Long): String?

    suspend fun patchComment(
        id: Long,
        commentDetail: String,
    ): String?

    suspend fun getMyComments(): List<CommentEntity>

}
