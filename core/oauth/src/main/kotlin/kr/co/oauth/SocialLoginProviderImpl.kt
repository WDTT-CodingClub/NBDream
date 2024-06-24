package kr.co.oauth

import kr.co.domain.entity.type.AuthType
import kr.co.domain.entity.LoginEntity
import kr.co.domain.proivder.SocialLoginProvider
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider

internal class SocialLoginProviderImpl @Inject constructor(
    private val providers: Map<AuthType, @JvmSuppressWildcards Provider<SocialLoginProvider>>
): SocialLoginProvider {

    override suspend fun login(type: AuthType): LoginEntity {
        val provider = providers[type]?.get()
        return provider?.login(type)
            ?: throw IllegalArgumentException("$type of parameter type is not allowed value")
    }

    override suspend fun logout(type: AuthType) {
        providers.values.forEach {
            val provider = it.get()
            Timber.d("Logging out with provider: ${provider.javaClass.name}")
            provider.logout(type)
        }
    }
}