package kr.co.data.repository

import kr.co.data.model.data.community.convertToEntityList
import kr.co.data.source.remote.CommunityRemoteDataSource
import kr.co.domain.entity.BulletinEntity
import kr.co.domain.entity.CropEntity
import kr.co.domain.repository.CommunityRepository
import javax.inject.Inject

internal class CommunityRepositoryImpl @Inject constructor(
    private val remote: CommunityRemoteDataSource,
) : CommunityRepository {

    override suspend fun postBulletin(
        content: String,
        dreamCrop: String,
        bulletinCategory: String,
        imageUrls: List<String>,
    ): Long {
        val result = remote.postBulletin(
            content = content,
            dreamCrop = dreamCrop,
            bulletinCategory = bulletinCategory,
            imageUrls = imageUrls,
        )
        return result.data ?: -1
    }

    override suspend fun getBulletins(
        keyword: String?,
        bulletinCategory: BulletinEntity.BulletinCategory,
        crop: CropEntity.Name,
        lastBulletinId: Long?,
    ): List<BulletinEntity> = remote.getBulletins(
        keyword = keyword,
        bulletinCategory = bulletinCategory.queryName,
        crop = crop.koreanName,
        lastBulletinId = lastBulletinId,
    ).convertToEntityList()

}
