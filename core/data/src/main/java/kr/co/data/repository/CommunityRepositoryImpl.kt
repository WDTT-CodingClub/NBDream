package kr.co.data.repository

import kr.co.data.source.remote.CommunityRemoteDataSource
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

}
