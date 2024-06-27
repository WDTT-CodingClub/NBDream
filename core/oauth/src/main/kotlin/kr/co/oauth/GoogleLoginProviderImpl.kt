package kr.co.oauth

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity.RESULT_OK
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.suspendCancellableCoroutine
import kr.co.common.ContextManager
import kr.co.domain.entity.type.AuthType
import kr.co.domain.entity.LoginEntity
import kr.co.domain.proivder.SocialLoginProvider
import kr.co.nbdream.core.oauth.BuildConfig
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

internal class GoogleLoginProviderImpl @Inject constructor(
) : SocialLoginProvider {
    //
//    private val context = ContextManager.getContext()
//
//    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//        .requestIdToken(BuildConfig.GOOGLE_API_KEY)
//        .requestEmail()
//        .build()
//
//    private val client by lazy {
//        GoogleSignIn.getClient(context, gso)
//    }
//
//
//    private var resultLauncher: ActivityResultLauncher<Intent>
//
//
//    init {
//        resultLauncher = context.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            if (result.resultCode == RESULT_OK && result.data != null) {
//                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
//
//                onSignInResult(task.getResult(ApiException::class.java))
//            } else {
//                onSignInError(Exception("로그인 취소"))
//            }
//        }
//    }
//
//
//    private var onSignInResult: (GoogleSignInAccount) -> Unit = {}
//    private var onSignInError: (Exception) -> Unit = {}
//
//    override suspend fun login(type: AuthType): LoginEntity {
//        return suspendCancellableCoroutine { continuation ->
//            onSignInResult = { account ->
//                continuation.resume(handleSignInResult(account))
//            }
//            onSignInError = { exception ->
//                continuation.resumeWithException(exception)
//            }
//
//            resultLauncher.launch(client.signInIntent)
//        }
//    }
//
//    private fun handleSignInResult(account: GoogleSignInAccount): LoginEntity {
//        val token = account.idToken ?: ""
//        return LoginEntity(AuthType.GOOGLE, token)
//    }
//
//    override suspend fun logout(type: AuthType) {
//        client.signOut()
//    }
    override suspend fun login(type: AuthType): LoginEntity {
        TODO("Not yet implemented")
    }

    override suspend fun logout(type: AuthType) {
        TODO("Not yet implemented")
    }

}
