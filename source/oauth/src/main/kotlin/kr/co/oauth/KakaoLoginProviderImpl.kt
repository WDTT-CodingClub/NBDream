package kr.co.oauth

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import kr.co.common.model.CustomException
import kr.co.domain.model.AuthType
import kr.co.domain.model.LoginResult
import kr.co.domain.proivder.SocialLoginProvider
import kr.co.nbdream.source.oauth.BuildConfig.*
import javax.inject.Inject
import kotlin.coroutines.resumeWithException

internal class KakaoLoginProviderImpl @Inject constructor(
    private val client: UserApiClient,
    private val context: Context,
) : SocialLoginProvider {

    init {
        KakaoSdk.init(
            context = context,
            KAKAO_NATIVE_APP_KEY
        )
    }

    override suspend fun login(type: AuthType): LoginResult {
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


    private suspend fun accessTokenToResult(accessToken: String): LoginResult =
        suspendCancellableCoroutine { continuation ->
            client.me { _, error ->
                error?.let {
                    continuation.resumeWithException(CustomException(cause = it))
                }
                continuation.resumeWith(
                    Result.success(
                        LoginResult(
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