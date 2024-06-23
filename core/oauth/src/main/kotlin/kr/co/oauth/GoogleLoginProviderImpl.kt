package kr.co.oauth

import kr.co.domain.entity.type.AuthType
import kr.co.domain.entity.LoginEntity
import kr.co.domain.proivder.SocialLoginProvider
import javax.inject.Inject

internal class GoogleLoginProviderImpl @Inject constructor(
) : SocialLoginProvider {
    override suspend fun login(type: AuthType): LoginEntity {
        return LoginEntity(AuthType.GOOGLE,"")
    }

    override suspend fun logout(type: AuthType) {

    }

}
