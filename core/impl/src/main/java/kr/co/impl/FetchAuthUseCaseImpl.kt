package kr.co.impl

import kotlinx.coroutines.flow.Flow
import kr.co.domain.repository.SessionRepository
import kr.co.domain.usecase.FetchAuthUseCase
import javax.inject.Inject
internal class FetchAuthUseCaseImpl @Inject constructor(
    private val repository: SessionRepository,
) : FetchAuthUseCase {
    override fun invoke(): Flow<String?> =
        repository.fetch()
}