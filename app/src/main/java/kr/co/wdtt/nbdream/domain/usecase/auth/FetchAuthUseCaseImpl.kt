package kr.co.wdtt.nbdream.domain.usecase.auth

import kotlinx.coroutines.flow.Flow
import kr.co.wdtt.core.domain.SessionRepository
import javax.inject.Inject

class FetchAuthUseCaseImpl @Inject constructor(
    private val repository: SessionRepository,
) : FetchAuthUseCase {
    override fun invoke(): Flow<String?> =
        repository.fetch()
}