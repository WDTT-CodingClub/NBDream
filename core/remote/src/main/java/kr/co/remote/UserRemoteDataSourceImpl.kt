package kr.co.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import kr.co.data.model.data.user.UserData
import kr.co.data.source.remote.UserRemoteDataSource
import kr.co.remote.mapper.GetUserRemoteMapper
import kr.co.remote.mapper.PutUserRemoteMapper
import kr.co.remote.model.Dto
import kr.co.remote.model.response.user.GetUserResponse
import javax.inject.Inject

internal class UserRemoteDataSourceImpl @Inject constructor(
    private val client: HttpClient
) : UserRemoteDataSource {

    companion object {
        private const val USER_INFO = "/api/member/my-page"
        private const val USER_UPDATE = "/api/member/profile"
    }

    override suspend fun fetch(): UserData {
        return client.get(USER_INFO)
            .body<Dto<GetUserResponse>>()
            .let { GetUserRemoteMapper.convert(it.data) }
    }

    override suspend fun update(user: UserData) {
        client.put(USER_UPDATE) {
            setBody(
                user.let(PutUserRemoteMapper::convert)
            )
        }
    }
}