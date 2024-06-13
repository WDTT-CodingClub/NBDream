package kr.co.remote.mapper

import kr.co.common.mapper.Mapper
import kr.co.data.model.data.auth.Auth
import kr.co.remote.model.response.auth.PostAuthResponse

internal object PostAuthMapper : Mapper<PostAuthResponse, Auth> {
    override fun convert(param: PostAuthResponse): Auth =
        with(param) {
            Auth(
                accessToken = accessToken,
                refreshToken = refreshToken,
            )
        }
}