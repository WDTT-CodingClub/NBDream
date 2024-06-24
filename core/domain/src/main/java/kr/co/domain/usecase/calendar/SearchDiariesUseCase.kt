package kr.co.domain.usecase.calendar

import kotlinx.coroutines.flow.Flow
import kr.co.domain.entity.DiaryEntity
import kr.co.domain.repository.DiaryRepository
import kr.co.domain.usecase.SuspendFlowUseCase
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchDiariesUseCase @Inject constructor(
    private val repository: DiaryRepository
) : SuspendFlowUseCase<SearchDiariesUseCase.Params, List<DiaryEntity>>() {
    data class Params(
        val crop: String,
        val query: String,
        val startDate: LocalDate,
        val endDate: LocalDate
    )

    override suspend fun build(params: Params?): Flow<List<DiaryEntity>> {
        checkNotNull(params)
        return repository.searchDiaries(
            params.crop,
            params.query,
            params.startDate,
            params.endDate
        )
    }
}