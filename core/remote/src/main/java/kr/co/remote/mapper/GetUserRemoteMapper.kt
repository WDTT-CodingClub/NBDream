package kr.co.remote.mapper

import kr.co.common.mapper.Mapper
import kr.co.data.model.data.user.UserData
import kr.co.remote.model.response.user.GetUserResponse

internal object GetUserRemoteMapper
    : Mapper<GetUserResponse, UserData> {
    override fun convert(param: GetUserResponse): UserData {
        return with(param) {
            UserData(
                name = nickname,
                address = address,
                profileImage = profileImageUrl,
                latitude = latitude,
                longitude = longitude,
                crops = crops,
            )
        }
    }

}