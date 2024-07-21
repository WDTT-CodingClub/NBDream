package kr.co.wdtt.nbdream

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import kr.co.common.ContextManager
import kr.co.common.util.FileUtil
import kr.co.ui.theme.NBDreamTheme
import kr.co.wdtt.nbdream.ui.DreamApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    companion object {
        private const val PERMISSION_POST_NOTIFICATIONS =
            android.Manifest.permission.POST_NOTIFICATIONS
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermission.launch(PERMISSION_POST_NOTIFICATIONS)
        }

        ContextManager.setContext(this)
        FileUtil.initialize(this)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                scrim = Color.TRANSPARENT,
                darkScrim = Color.TRANSPARENT
            )
        )
        setContent {
            NBDreamTheme {
                DreamApp()
            }
        }
    }

    private val requestPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            /* TODO */
        } else {
            /* TODO */
        }
    }

}
