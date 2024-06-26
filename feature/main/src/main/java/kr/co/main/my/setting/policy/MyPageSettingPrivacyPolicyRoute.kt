package kr.co.main.my.setting.policy

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import kr.co.main.R
import kr.co.ui.ext.scaffoldBackground
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.colors
import kr.co.ui.widget.DreamCenterTopAppBar

@Composable
internal fun MyPageSettingPrivacyPolicyRoute(
    popBackStack: () -> Unit = {},
) {
    MyPageSettingPrivacyPolicyScreen(
        popBackStack = popBackStack
    )
}

@Composable
private fun MyPageSettingPrivacyPolicyScreen(
    popBackStack: () -> Unit,
) {
    Scaffold(
        containerColor = MaterialTheme.colors.white,
        topBar = {
            DreamCenterTopAppBar(
                title = "약관 및 개인정보 처리 방침",
                colorBackground = true,
                navigationIcon = {
                    IconButton(onClick = popBackStack) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = stringResource(id = R.string.feature_main_pop_back_stack)
                        )
                    }
                }
            )
        }
    ) { scaffoldPadding ->
        Modifier.padding(scaffoldPadding)
        AndroidView(
            modifier = Modifier,
            factory = { context ->
                WebView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    settings.useWideViewPort = true
                    settings.loadWithOverviewMode = true
                    settings.cacheMode = android.webkit.WebSettings.LOAD_NO_CACHE
                    webViewClient = WebViewClient()
                    loadUrl("https://meadow-garnet-a3c.notion.site/01e4044f12614ab5ae3ad97acd9a9ad7?pvs=4")
                }
            },
            update = {
                it.loadUrl("https://meadow-garnet-a3c.notion.site/01e4044f12614ab5ae3ad97acd9a9ad7?pvs=4")
            }
        )
    }
}

@Preview
@Composable
private fun Preview() {
    NBDreamTheme {
        MyPageSettingPrivacyPolicyRoute()
    }
}