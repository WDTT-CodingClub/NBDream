package kr.co.data.mapper

import kr.co.common.mapper.BaseMapper
import kr.co.data.model.data.user.UserData
import kr.co.domain.entity.UserEntity

internal object UserMapper
    :BaseMapper<UserData, UserEntity>(){
    override fun toRight(model: UserData): UserEntity {
        return with(model) {
            UserEntity(
                name = name,
                address = address,
                profileImage = profileImage,
                longitude = longitude,
                latitude = latitude,
                crops = crops
            )
        }
    }

    override fun toLeft(entity: UserEntity): UserData {
        return with(entity) {
            UserData(
                name = name,
                address = address,
                profileImage = profileImage,
                longitude = longitude,
                latitude = latitude,
                crops = crops
            )
        }
    }
}