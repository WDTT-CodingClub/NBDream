package kr.co.wdtt.nbdream.domain.repository

import kr.co.domain.entity.BulletinEntity

interface BulletinRepository {

    suspend fun getBulletin(bulletinId: String): BulletinEntity
    suspend fun startWritingBulletin(): String
    suspend fun cancelWritingBulletin(bulletinId: String)
    suspend fun postBulletin(
        bulletinId: String,
        content: String,
        crop: String,
        bulletinCategory: String,
    ): String

    suspend fun deleteBulletin(bulletinId: String)
    suspend fun updateBulletin(
        bulletinId: String,
        content: String,
        crop: String,
        bulletinCategory: String,
    ): String

    suspend fun postComment(content: String)
    suspend fun deleteComment(commentId: String)
    suspend fun updateComment(
        commentId: String,
        content: String,
    )

}
