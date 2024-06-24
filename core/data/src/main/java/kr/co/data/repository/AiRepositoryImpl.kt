package kr.co.data.repository

import kr.co.data.source.remote.AiRemoteDataSource
import kr.co.domain.repository.AiRepository
import javax.inject.Inject

internal class AiRepositoryImpl @Inject constructor(
    private val remote: AiRemoteDataSource
): AiRepository
{
    override suspend fun send(m: String): String {
        return remote.send(m)
    }
}
