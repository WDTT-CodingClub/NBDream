package kr.co.domain.usecase

import kotlinx.coroutines.flow.Flow

abstract class FlowUseCase<PARAMS, RESULT> {
    protected abstract fun build(prams: PARAMS? = null): Flow<RESULT>
    operator fun invoke(params: PARAMS? = null): Flow<RESULT> = build(params)
}