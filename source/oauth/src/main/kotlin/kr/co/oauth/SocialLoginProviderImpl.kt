package kr.co.oauth

import kr.co.domain.model.AuthType
import kr.co.domain.model.LoginResult
import kr.co.domain.proivder.SocialLoginProvider
import javax.inject.Inject
import javax.inject.Provider

internal class SocialLoginProviderImpl @Inject constructor(
    private val providers: Map<AuthType, @JvmSuppressWildcards Provider<SocialLoginProvider>>
): SocialLoginProvider {

    override suspend fun login(type: AuthType): LoginResult {
        return providers[type]?.run {
            get().login(type)
        } ?: throw IllegalArgumentException("$type of parameter type is not allowed value")
    }

    override suspend fun logout(type: AuthType) {
        providers.values.map { it.get().logout(type) }
    }
}