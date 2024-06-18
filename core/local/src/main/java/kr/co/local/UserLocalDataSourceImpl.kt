package kr.co.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kr.co.data.model.data.user.UserData
import kr.co.data.source.local.UserLocalDataSource
import kr.co.local.mapper.UserLocalMapper
import kr.co.local.room.dao.UserDao
import javax.inject.Inject

internal class UserLocalDataSourceImpl @Inject constructor(
    private val dao: UserDao
) : UserLocalDataSource {
    override fun fetch(userId: String): Flow<UserData> {
        return dao.fetchUser(userId)
            .filterNotNull()
            .map(UserLocalMapper::toLeft)
    }

    override suspend fun save(user: UserData) {
        dao.insert(user.let(UserLocalMapper::toRight))
    }

    override suspend fun clear() {
        dao.deleteAll()
    }
}