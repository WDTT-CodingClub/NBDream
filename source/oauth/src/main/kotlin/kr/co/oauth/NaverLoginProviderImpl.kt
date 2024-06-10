package kr.co.oauth

import android.content.Context
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import kr.co.domain.model.AuthType
import kr.co.domain.model.LoginResult
import kr.co.domain.proivder.SocialLoginProvider
import javax.inject.Inject
import javax.inject.Named

internal class NaverLoginProviderImpl @Inject constructor(
) : SocialLoginProvider {
    override suspend fun login(type: AuthType): LoginResult {
        return LoginResult(AuthType.NAVER,"")
    }

    override suspend fun logout(type: AuthType) {

    }

}
