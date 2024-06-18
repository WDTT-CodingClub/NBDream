package kr.co.local.mapper

import kr.co.common.mapper.BaseMapper
import kr.co.data.model.data.user.UserData
import kr.co.local.model.UserEntity

internal object UserLocalMapper
    :BaseMapper<UserData, UserEntity>(){
    override fun toRight(model: UserData): UserEntity {
        return with(model) {
            UserEntity(
                name = name,
                profileImage = profileImage,
                address = address,
                latitude = latitude,
                longitude = longitude,
                crops = crops
            )
        }
    }

    override fun toLeft(entity: UserEntity): UserData {
        return with(entity) {
            UserData(
                name = name,
                profileImage = profileImage,
                address = address,
                latitude = latitude,
                longitude = longitude,
                crops = crops
            )
        }
    }
}