package kr.co.data.mapper.type

import kr.co.common.mapper.BaseMapper
import kr.co.data.model.type.AuthTypeData
import kr.co.domain.entity.type.AuthType

internal object AuthTypeDataMapper
    :BaseMapper<AuthTypeData, AuthType> (){
    override fun toRight(model: AuthTypeData) =
        when(model){
            AuthTypeData.KAKAO -> AuthType.KAKAO
            AuthTypeData.NAVER -> AuthType.NAVER
            AuthTypeData.GOOGLE -> AuthType.GOOGLE
        }

    override fun toLeft(entity: AuthType) =
        when(entity){
            AuthType.KAKAO -> AuthTypeData.KAKAO
            AuthType.NAVER -> AuthTypeData.NAVER
            AuthType.GOOGLE -> AuthTypeData.GOOGLE
        }
}