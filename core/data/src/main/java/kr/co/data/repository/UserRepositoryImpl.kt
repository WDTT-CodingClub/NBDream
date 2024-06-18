package kr.co.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kr.co.data.mapper.UserMapper
import kr.co.data.source.local.SessionLocalDataSource
import kr.co.data.source.local.UserLocalDataSource
import kr.co.data.source.remote.UserRemoteDataSource
import kr.co.domain.entity.UserEntity
import kr.co.domain.repository.UserRepository
import javax.inject.Inject

internal class UserRepositoryImpl @Inject constructor(
    private val local: UserLocalDataSource,
    private val session: SessionLocalDataSource,
    private val remote: UserRemoteDataSource
): UserRepository {

    private fun getUser() =
        session.fetchUserName()
            .filterNotNull()
            .flatMapLatest {
                local.fetch(it)
            }

    override suspend fun fetch(): UserEntity =
        remote.fetch()
            .let(UserMapper::toRight)

    override fun fetchLocal(): Flow<UserEntity> =
        getUser()
            .map(UserMapper::toRight)

    override suspend fun save(user: UserEntity) {
        local.save(user.let(UserMapper::toLeft))
    }

    override suspend fun update(user: UserEntity) {
        remote.update(user.let(UserMapper::toLeft))
    }

    override suspend fun delete() {
        local.clear()
        session.removeAll()
    }

    override suspend fun clearAll() {
        local.clear()
    }

}