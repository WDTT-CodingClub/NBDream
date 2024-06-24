package kr.co.data.repository

import kr.co.data.mapper.type.AuthTypeDataMapper
import kr.co.data.source.local.SessionLocalDataSource
import kr.co.data.source.remote.AuthRemoteDataSource
import kr.co.domain.entity.type.AuthType
import kr.co.domain.repository.AuthRepository
import javax.inject.Inject

internal class AuthRepositoryImpl @Inject constructor(
    private val remote: AuthRemoteDataSource,
    private val session: SessionLocalDataSource
) : AuthRepository {

    override suspend fun login(type: AuthType, token: String) {
        remote.login(
            type = type.let(AuthTypeDataMapper::toLeft),
            token = token
        ).also {
            session.updateAccessToken(it.accessToken)
            session.updateRefreshToken(it.refreshToken)
        }
    }

    override suspend fun check() {
        remote.getAuthToken(
            accessToken = session.getAccessToken()?: return,
            refreshToken = session.getRefreshToken()?: return
        ).also {
            session.updateAccessToken(it.accessToken)
            session.updateRefreshToken(it.refreshToken)
        }
    }
}