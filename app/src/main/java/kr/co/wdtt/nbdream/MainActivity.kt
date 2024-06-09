package kr.co.wdtt.nbdream

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import kr.co.common.ContextManager
import kr.co.wdtt.nbdream.ui.DreamApp
import kr.co.ui.theme.NBDreamTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ContextManager.setContext(this)
        enableEdgeToEdge()
        setContent {
            NBDreamTheme {
                DreamApp()
            }
        }
    }
}
