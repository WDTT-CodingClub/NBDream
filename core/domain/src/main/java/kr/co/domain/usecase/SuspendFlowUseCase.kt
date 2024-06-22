package kr.co.domain.usecase

import kotlinx.coroutines.flow.Flow

abstract class SuspendFlowUseCase<PARAMS, RESULT> {
    protected abstract suspend fun build(params: PARAMS? = null): Flow<RESULT>
    suspend operator fun invoke(params: PARAMS? = null): Flow<RESULT> = build(params)
}