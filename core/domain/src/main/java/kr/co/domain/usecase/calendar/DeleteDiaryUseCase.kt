package kr.co.domain.usecase.calendar

import kr.co.domain.repository.DiaryRepository
import kr.co.domain.usecase.SuspendUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteDiaryUseCase @Inject constructor(
    private val repository: DiaryRepository
) : SuspendUseCase<DeleteDiaryUseCase.Params, Unit>(){
    data class Params(
        val id:Int
    )

    override suspend fun build(params: Params?) {
        checkNotNull(params)
        return repository.deleteDiary(params.id)
    }
}