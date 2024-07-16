package kr.co.main.calendar.screen.farmWorkInfoScreen

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.Arrowleft
import kr.co.ui.widget.DreamCenterTopAppBar

@Composable
internal fun FarmWorkInfoRoute(
    popBackStack: () -> Unit = {},
    viewModel: FarmWorkInfoViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    FarmWorkInfoScreen(
        modifier = Modifier.fillMaxSize(),
        state = state,
        popBackStack = popBackStack
    )
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
private fun FarmWorkInfoScreen(
    state: FarmWorkInfoViewModel.FarmWorkInfoScreenState,
    popBackStack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            DreamCenterTopAppBar(
                title = state.title,
                navigationIcon = {
                    Icon(
                        modifier = Modifier.clickable {
                            popBackStack()
                        },
                        imageVector = DreamIcon.Arrowleft,
                        contentDescription = ""
                    )
                }
            )
        }
    ) { scaffoldPadding ->
        AndroidView(
            modifier = Modifier.padding(scaffoldPadding),
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
                    settings.cacheMode = android.webkit.WebSettings.LOAD_CACHE_ELSE_NETWORK
                    webViewClient = WebViewClient()
                    loadUrl(state.videoUrl)
                }
            },
            update = {
                it.loadUrl(state.videoUrl)
            }
        )
    }
}