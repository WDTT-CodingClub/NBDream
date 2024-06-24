package kr.co.data.repository

import kr.co.data.model.data.community.convertToEntities
import kr.co.data.model.data.community.convertToEntity
import kr.co.data.source.remote.CommunityRemoteDataSource
import kr.co.domain.entity.BulletinEntity
import kr.co.domain.entity.type.CropType
import kr.co.domain.repository.CommunityRepository
import javax.inject.Inject

internal class CommunityRepositoryImpl @Inject constructor(
    private val remote: CommunityRemoteDataSource,
) : CommunityRepository {

    override suspend fun postBulletin(
        content: String,
        crop: CropType,
        bulletinCategory: BulletinEntity.BulletinCategory,
        imageUrls: List<String>,
    ): Long {
        val result = remote.postBulletin(
            content = content,
            crop = crop.koreanName,
            bulletinCategory = bulletinCategory.queryName,
            imageUrls = imageUrls,
        )
        return result.data ?: -1
    }

    override suspend fun deleteBulletin(
        id: Long,
    ): Boolean = remote.deleteBulletin(
        id = id,
    )

    override suspend fun putBulletin(
        id: Long,
        content: String,
        crop: CropType,
        bulletinCategory: BulletinEntity.BulletinCategory,
        imageUrls: List<String>,
    ): Long = remote.putBulletin(
        id = id,
        content = content,
        crop = crop.koreanName,
        bulletinCategory = bulletinCategory.queryName,
        imageUrls = imageUrls,
    ).data ?: -1

    override suspend fun bookmarkBulletin(
        id: Long,
    ): Boolean = remote.bookmarkBulletin(
        id = id,
    )

    override suspend fun getBulletins(
        keyword: String?,
        bulletinCategory: BulletinEntity.BulletinCategory,
        crop: CropType,
        lastBulletinId: Long?,
    ): List<BulletinEntity> = remote.getBulletins(
        keyword = keyword,
        bulletinCategory = bulletinCategory.queryName,
        crop = crop.koreanName,
        lastBulletinId = lastBulletinId,
    ).convertToEntities()

    override suspend fun getMyBulletins(
        lastBulletinId: Long?,
    ): List<BulletinEntity> = remote.getMyBulletins(
        lastBulletinId = lastBulletinId,
    ).convertToEntities()

    override suspend fun getBookmarks(
        lastBulletinId: Long?,
    ): List<BulletinEntity> = remote.getBookmarks(
        lastBulletinId = lastBulletinId,
    ).convertToEntities()

    override suspend fun getBulletinDetail(
        bulletinId: Long,
    ): BulletinEntity? = remote.getBulletinDetail(
        bulletinId = bulletinId,
    ).data?.convertToEntity()

    override suspend fun postComment(
        id: Long,
        commentDetail: String,
    ): Long {
        val result = remote.postComment(
            id = id,
            commentDetail = commentDetail,
        )
        return result.data ?: -1
    }

    override suspend fun deleteComment(
        id: Long,
    ): String? {
        val result = remote.deleteComment(
            id = id,
        )
        return result.data
    }

    override suspend fun patchComment(
        id: Long,
        commentDetail: String,
    ): String? {
        val result = remote.patchComment(
            id = id,
            commentDetail = commentDetail,
        )
        return result.data
    }

    override suspend fun getMyComments() = remote.getMyComments().convertToEntities()

}
