package kr.co.domain.usecase.calendar

import kotlinx.coroutines.flow.Flow
import kr.co.domain.entity.DiaryEntity
import kr.co.domain.repository.DiaryRepository
import kr.co.domain.usecase.FlowUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchDiariesUseCase @Inject constructor(
    //private val repository: DiaryRepository
): FlowUseCase<SearchDiariesUseCase.Params, List<DiaryEntity>>() {
    data class Params(
        val crop: String,
        val query: String,
        val startDate: String,
        val endDate: String
    )

    override fun build(params: Params?): Flow<List<DiaryEntity>> {
        TODO ("not yet implemented")
        //if(params == null) throw IllegalArgumentException("params cannot be null")
        //return repository.searchDiaries(params.crop, params.query, params.startDate, params.endDate)
    }
}