package kr.co.wdtt.nbdream.domain.usecase.auth

import kotlinx.coroutines.flow.Flow

interface FetchAuthUseCase {
    operator fun invoke(): Flow<String?>
}