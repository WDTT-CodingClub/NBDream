package kr.co.oauth

import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import kotlinx.coroutines.suspendCancellableCoroutine
import kr.co.common.ContextManager
import kr.co.domain.model.AuthType
import kr.co.domain.model.LoginResult
import kr.co.domain.proivder.SocialLoginProvider
import kr.co.nbdream.core.oauth.BuildConfig.OAUTH_CLIENT_ID
import kr.co.nbdream.core.oauth.BuildConfig.OAUTH_CLIENT_NAME
import kr.co.nbdream.core.oauth.BuildConfig.OAUTH_CLIENT_SECRET
import javax.inject.Inject
import kotlin.coroutines.resumeWithException

internal class NaverLoginProviderImpl @Inject constructor(
) : SocialLoginProvider {

    private val context get() = ContextManager.getContext()
    init {
        NaverIdLoginSDK.initialize(
            context = context,
            clientId = OAUTH_CLIENT_ID,
            clientSecret = OAUTH_CLIENT_SECRET,
            clientName = OAUTH_CLIENT_NAME
        )
    }

    override suspend fun login(type: AuthType): LoginResult {
        return suspendCancellableCoroutine { continuation ->
            val oauthLoginCallback = object : OAuthLoginCallback {
                override fun onSuccess() {
                    val accessToken = NaverIdLoginSDK.getAccessToken().orEmpty()
                    continuation.resume(LoginResult(AuthType.NAVER, accessToken)) {}
                }

                override fun onFailure(httpStatus: Int, message: String) {
                    continuation.resumeWithException(Exception("Login failed: $message"))
                }

                override fun onError(errorCode: Int, message: String) {
                    onFailure(errorCode, message)
                }
            }

            NaverIdLoginSDK.authenticate(context, oauthLoginCallback)
        }
    }

    override suspend fun logout(type: AuthType) {
        NaverIdLoginSDK.logout()
    }

}
