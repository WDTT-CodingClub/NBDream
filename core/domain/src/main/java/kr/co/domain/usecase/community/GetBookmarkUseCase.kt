package kr.co.domain.usecase.community

import kr.co.domain.entity.BulletinEntity
import kr.co.domain.repository.CommunityRepository
import kr.co.domain.usecase.SuspendUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetBookmarkUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
) : SuspendUseCase<Long, List<BulletinEntity>>() {
    override suspend fun build(params: Long?): List<BulletinEntity> {

        return communityRepository.getBookmarks(params)
    }
}