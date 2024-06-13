package kr.co.remote.mapper.type

import kr.co.common.mapper.BaseMapper
import kr.co.data.model.type.AuthTypeData
import kr.co.remote.model.type.AuthTypeRemote

internal object AuthTypeRemoteMapper
    : BaseMapper<AuthTypeRemote, AuthTypeData>() {
    override fun toRight(model: AuthTypeRemote): AuthTypeData =
        when(model) {
            AuthTypeRemote.KAKAO -> AuthTypeData.KAKAO
            AuthTypeRemote.NAVER -> AuthTypeData.NAVER
            AuthTypeRemote.GOOGLE -> AuthTypeData.GOOGLE
        }

    override fun toLeft(entity: AuthTypeData): AuthTypeRemote =
        when(entity) {
            AuthTypeData.KAKAO -> AuthTypeRemote.KAKAO
            AuthTypeData.NAVER -> AuthTypeRemote.NAVER
            AuthTypeData.GOOGLE -> AuthTypeRemote.GOOGLE
        }
}