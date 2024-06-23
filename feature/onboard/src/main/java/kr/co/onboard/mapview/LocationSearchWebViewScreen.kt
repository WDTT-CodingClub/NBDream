package kr.co.onboard.mapview

import android.os.Handler
import android.os.Looper
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import timber.log.Timber

@Composable
fun LocationSearchWebViewScreen(
    modifier: Modifier,
    onAddressSelected: (String, String) -> Unit
) {
    Scaffold(
        topBar = {}
    ) { paddingValues ->
        Box(
            modifier = modifier.padding(paddingValues)
        ) {
            AndroidView(
                factory = { context ->
                    WebView(context).apply {
                        settings.javaScriptEnabled = true
                        settings.domStorageEnabled = true
                        webViewClient = object : WebViewClient() {
                            override fun onPageFinished(view: WebView?, url: String?) {
                                loadUrl("javascript:sample2_execDaumPostcode();")
                            }
                        }
                        webChromeClient = WebChromeClient()
                        addJavascriptInterface(WebAppInterface(onAddressSelected), "Android")
                        loadUrl("https://seulseul-35d52.web.app")
                    }
                },
                modifier = modifier.fillMaxSize()
            )
        }
    }
}

class WebAppInterface(private val onAddressSelected: (String, String) -> Unit) {
    private val handler = Handler(Looper.getMainLooper())

    @JavascriptInterface
    fun processDATA(fullRoadAddr: String, jibunAddr: String) {
        handler.post {
            try {
                Timber.tag("WebAppInterface").d("fullRoadAddr: $fullRoadAddr, jibunAddr: $jibunAddr")
                onAddressSelected(fullRoadAddr, jibunAddr)
            } catch (e: Exception) {
                Timber.tag("WebAppInterface").e(e, "Error processing data")
            }
        }
    }
}