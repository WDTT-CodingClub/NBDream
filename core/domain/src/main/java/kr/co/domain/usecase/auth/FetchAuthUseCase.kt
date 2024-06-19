package kr.co.domain.usecase.auth

import kotlinx.coroutines.flow.Flow
import kr.co.domain.repository.SessionRepository
import kr.co.domain.usecase.FlowUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FetchAuthUseCase @Inject constructor(
    private val repository: SessionRepository,
) : FlowUseCase<Unit, String?>() {
    override fun build(params: Unit?): Flow<String?> =
        repository.fetch()
}