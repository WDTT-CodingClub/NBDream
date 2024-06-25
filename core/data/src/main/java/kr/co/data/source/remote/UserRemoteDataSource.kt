package kr.co.data.source.remote

import kr.co.data.model.data.user.UserData

interface UserRemoteDataSource {
    suspend fun fetch(): UserData

    suspend fun update(user: UserData)
    suspend fun delete()
    suspend fun postReason(reason: String, otherReason: String)
}
