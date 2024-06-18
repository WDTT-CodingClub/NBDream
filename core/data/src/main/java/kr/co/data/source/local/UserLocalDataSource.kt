package kr.co.data.source.local

import kotlinx.coroutines.flow.Flow
import kr.co.data.model.data.user.UserData

interface UserLocalDataSource {
    fun fetch(userId: String): Flow<UserData>

    suspend fun save(user: UserData)

    suspend fun clear()
}
