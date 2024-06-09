package kr.co.domain.usecase

import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface FetchAuthUseCase {
    operator fun invoke(): Flow<String?>
}