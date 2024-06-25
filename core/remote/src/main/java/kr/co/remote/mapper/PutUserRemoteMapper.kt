package kr.co.remote.mapper

import kr.co.common.mapper.Mapper
import kr.co.data.model.data.user.UserData
import kr.co.remote.model.request.user.PutUserRequest

internal object PutUserRemoteMapper
    :Mapper<UserData ,PutUserRequest>{
    override fun convert(param: UserData): PutUserRequest {
        return with(param) {
            PutUserRequest (
                nickname = name,
                profileImageUrl = profileImage,
                bjdCode = bjdCode,
                address = address,
                latitude = latitude,
                longitude = longitude,
                crops = crops
            )
        }
    }
}