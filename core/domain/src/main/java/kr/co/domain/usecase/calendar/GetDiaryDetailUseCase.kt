package kr.co.domain.usecase.calendar

import kr.co.domain.entity.DiaryEntity
import kr.co.domain.repository.DiaryRepository
import kr.co.domain.usecase.SuspendUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetDiaryDetailUseCase @Inject constructor(
    private val repository: DiaryRepository
): SuspendUseCase<GetDiaryDetailUseCase.Params, DiaryEntity>() {
    data class Params(
        val id:Long
    )

    override suspend fun build(params: Params?): DiaryEntity {
        checkNotNull(params)
        return repository.getDiaryDetail(params.id)
    }
}