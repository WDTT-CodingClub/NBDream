package kr.co.domain.usecase

import kotlinx.coroutines.flow.Flow
import kr.co.domain.repository.SessionRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FetchAuthUseCase @Inject constructor(
    private val repository: SessionRepository,
) : FlowUseCase<Unit, String?>() {
    override fun build(prams: Unit?): Flow<String?> =
        repository.fetch()
}