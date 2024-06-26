package kr.co.domain.usecase.community

import kr.co.domain.entity.CommentEntity
import kr.co.domain.repository.CommunityRepository
import kr.co.domain.usecase.SuspendUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetMyCommentsUseCase @Inject constructor(
    private val communityRepository: CommunityRepository
): SuspendUseCase<Unit, List<CommentEntity>>() {
    override suspend fun build(params: Unit?): List<CommentEntity> {

        return communityRepository.getMyComments()
    }
}