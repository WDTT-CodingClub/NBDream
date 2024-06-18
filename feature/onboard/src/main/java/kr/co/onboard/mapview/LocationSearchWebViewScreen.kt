package kr.co.onboard.mapview

import android.os.Handler
import android.os.Looper
import android.util.Log
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

@Composable
fun LocationSearchWebViewScreen(
    modifier: Modifier,
    addressSelectionListener: AddressSelectionListener
) {
    Scaffold(
        topBar = {

        }
    ) { paddingValues ->
        Box(
            modifier = modifier.padding(paddingValues)
        ){
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
                        addJavascriptInterface(WebAppInterface(addressSelectionListener), "Android")
                        loadUrl("https://seulseul-35d52.web.app")
                    }
                },
                modifier = modifier
                    .fillMaxSize()
            )
        }
    }
}

interface AddressSelectionListener {
    fun onAddressSelected(fullRoadAddr: String, jibunAddr: String)
}
class WebAppInterface(private val listener: AddressSelectionListener) {
    private val handler = Handler(Looper.getMainLooper())

    @JavascriptInterface
    fun processDATA(fullRoadAddr: String, jibunAddr: String) {
        handler.post {
            try {
                Log.d("WebAppInterface", "fullRoadAddr: $fullRoadAddr, jibunAddr: $jibunAddr")
                listener.onAddressSelected(fullRoadAddr, jibunAddr)
            } catch (e: Exception) {
                Log.e("WebAppInterface", "Error processing data", e)
            }
        }
    }
}