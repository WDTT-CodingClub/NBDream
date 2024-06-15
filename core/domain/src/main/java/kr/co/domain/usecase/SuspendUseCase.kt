package kr.co.domain.usecase

abstract class SuspendUseCase<PARAMS, RESULT> {
    protected abstract suspend fun build(params: PARAMS? = null): RESULT
    suspend operator fun invoke(params: PARAMS? = null): RESULT = build(params)
}