package kr.co.data.repository

import kr.co.data.source.remote.FcmRemoteDataSource
import kr.co.domain.repository.FcmRepository
import javax.inject.Inject

internal class FcmRepositoryImpl @Inject constructor(
    private val remote: FcmRemoteDataSource,
) : FcmRepository {
    override suspend fun saveFcmToken(token: String) {
        remote.saveFcmToken(token)
    }

    override suspend fun invalidateFcmToken() {
        remote.expireFcmToken()
    }

    override suspend fun sendFcmMessage(token: String, title: String, body: String) {
        remote.sendFcmMessage(token, title, body)
    }
}