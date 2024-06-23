package kr.co.oauth

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.suspendCancellableCoroutine
import kr.co.common.ContextManager
import kr.co.common.model.CustomException
import kr.co.domain.entity.type.AuthType
import kr.co.domain.entity.LoginEntity
import kr.co.domain.proivder.SocialLoginProvider
import kr.co.nbdream.core.oauth.BuildConfig.KAKAO_NATIVE_APP_KEY
import javax.inject.Inject
import kotlin.coroutines.resumeWithException

internal class KakaoLoginProviderImpl @Inject constructor(
) : SocialLoginProvider {

    private val client: UserApiClient by lazy {
        UserApiClient.instance
    }

    private val context get() = ContextManager.getContext()

    init {
        KakaoSdk.init(
            context = context,
            KAKAO_NATIVE_APP_KEY
        )
    }

    override suspend fun login(type: AuthType): LoginEntity {
        runCatching { logout(type) }

        return accessTokenToResult(provider(context).accessToken)
    }

    private suspend fun provider(context: Context): OAuthToken =
        if (client.isKakaoTalkLoginAvailable(context)) {
            loginWithKakao(context)
        } else {
            loginWithKaKaoAccount(context)
        }

    private suspend fun loginWithKakao(context: Context) =
        suspendCancellableCoroutine { continuation ->
            client.loginWithKakaoTalk(context) { token, error ->
                error?.let {
                    if (it.message == USER_CANCELLED) return@loginWithKakaoTalk
                    continuation.resumeWithException(CustomException(cause = it))
                    return@loginWithKakaoTalk
                }
                continuation.resumeWith(Result.success(token ?: return@loginWithKakaoTalk))
            }
        }

    private suspend fun loginWithKaKaoAccount(context: Context) =
        suspendCancellableCoroutine { continuation ->
            client.loginWithKakaoAccount(context) { token, error ->
                error?.let {
                    if (it.message == USER_CANCELLED) return@loginWithKakaoAccount
                    continuation.resumeWithException(CustomException(cause = it))
                    return@loginWithKakaoAccount
                }
                continuation.resumeWith(
                    Result.success(token ?: return@loginWithKakaoAccount)
                )
            }
        }


    private suspend fun accessTokenToResult(accessToken: String): LoginEntity =
        suspendCancellableCoroutine { continuation ->
            client.me { _, error ->
                error?.let {
                    continuation.resumeWithException(CustomException(cause = it))
                }
                continuation.resumeWith(
                    Result.success(
                        LoginEntity(
                            type = AuthType.KAKAO,
                            token = accessToken
                        )
                    )
                )
            }
        }


    override suspend fun logout(type: AuthType) {
        client.logout {  }
    }

    companion object {
        private const val USER_CANCELLED = "user cancelled."
    }
}