package kr.co.domain.usecase.calendar

import kotlinx.coroutines.flow.Flow
import kr.co.domain.entity.CropEntity
import kr.co.domain.entity.DiaryEntity
import kr.co.domain.repository.DiaryRepository
import kr.co.domain.usecase.SuspendFlowUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetDiariesUseCase @Inject constructor(
    private val repository: DiaryRepository
): SuspendFlowUseCase<GetDiariesUseCase.Params, List<DiaryEntity>>() {

    data class Params(
        val crop:CropEntity,
        val year:Int,
        val month:Int
    )

    override suspend fun build(params: Params?): Flow<List<DiaryEntity>> {
        checkNotNull(params)
        return repository.getDiaries(
            crop = params.crop.type.koreanName,
            year = params.year,
            month = params.month
        )
    }
}