package kr.co.wdtt.nbdream

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Base64
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import kr.co.common.ContextManager
import kr.co.wdtt.nbdream.ui.DreamApp
import kr.co.ui.theme.NBDreamTheme
import timber.log.Timber
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ContextManager.setContext(this)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                scrim = Color.TRANSPARENT,
                darkScrim = Color.TRANSPARENT
            )
        )

        printHashKey()

        setContent {
            NBDreamTheme {
                DreamApp()
            }
        }
    }

    private fun printHashKey() {
        try {
            val info: PackageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey = Base64.encodeToString(md.digest(), Base64.NO_WRAP)
                Timber.d("KeyHash: $hashKey")
            }
        } catch (e: NoSuchAlgorithmException) {
            Timber.e(e, "NoSuchAlgorithmException")
        } catch (e: Exception) {
            Timber.e(e, "Exception")
        }
    }
}
