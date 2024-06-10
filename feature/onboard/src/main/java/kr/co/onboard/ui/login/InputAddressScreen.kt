package kr.co.onboard.ui.login

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import kr.co.ui.theme.ColorSet.Dream.lightColors
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.typo


@Composable
fun Title(
    title: String,
) {
    Text(
        title,
        style = MaterialTheme.typo.headerB,
        modifier = Modifier
            .fillMaxWidth(),
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun StepProgressBar(
    modifier: Modifier = Modifier,
    color: Color
) {
    Box(
        modifier = modifier
            .height(10.dp)
            .background(
                color = color,
                shape = RoundedCornerShape(4.dp)
            )
            .then(
                if (color == Color.Transparent)
                    Modifier.border(0.5.dp, Color.Gray, RoundedCornerShape(4.dp))
                else
                    Modifier
            )

    )
}

@Composable
fun DynamicStepProgressBars(
    colors: List<Color>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        colors.forEach { color ->
            StepProgressBar(
                color = color,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun DescriptionText(
    text: String
){
    Text(
        text,
        style = MaterialTheme.typo.titleSB,
    )
}
@Composable
private fun Address(
    fullRoadAddr: String,
    jibunAddr: String,
    onFullRoadAddrChange: (String) -> Unit,
    onJubunAddrChange: (String) -> Unit,
    onSearchClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding()
    ) {
        StepText("1/2", modifier = Modifier)
        DescriptionText("내 농장의 주소지를 입력해주세요")
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomTextField(
                fullRoadAddr,
                onFullRoadAddrChange,
                modifier = Modifier
                    .weight(3f),
                placeholder = "주소를 입력해주세요"
            )
            Button(
                onClick = onSearchClick,
                contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = lightColors.secondary
                ),
                modifier = Modifier
                    .padding(start = 8.dp)
                    .border(1.dp, lightColors.secondary, RoundedCornerShape(12.dp))
                    .weight(1f)
                    .height(35.dp)
            ) {
                Text("주소 찾기", style = TextStyle(fontSize = 16.sp, color = lightColors.secondary)) // Adjust text style
            }
        }
        NextButton()
    }
}
@Composable
fun NextButton(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ){
        Column(
            modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = lightColors.secondary
                ),
            ) {
                Text("나중에 입력할게요")
            }
            Button(
                onClick = {},
                modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = lightColors.primary,
                    contentColor = Color.White
                ),
            ) {
                Text(
                    "다음으로",
                    style = MaterialTheme.typo.titleSB,
                )
            }
        }

    }
}
@Composable
fun LocationSearchScreen(
    navController: NavController,
    initialFullRoadAddr: String,
    initialJibunAddr: String,
    onAddressSelected: (String, String) -> Unit
) {
    var fullRoadAddr by remember { mutableStateOf(initialFullRoadAddr) }
    var jibunAddr by remember { mutableStateOf(initialJibunAddr) }

    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle

    // savedStateHandle을 관찰하고 변화에 반응하기 위해 DisposableEffect 사용
    DisposableEffect(savedStateHandle) {
        // 옵저버 설정
        val observer = Observer<String> { newValue ->
            if (newValue != null) {
                val currentRoadAddr = savedStateHandle?.get<String>("fullRoadAddr")
                val currentJibunAddr = savedStateHandle?.get<String>("jibunAddr")

                if (currentRoadAddr != null && currentRoadAddr != fullRoadAddr) {
                    fullRoadAddr = currentRoadAddr
                    onAddressSelected(currentRoadAddr, jibunAddr)
                }

                if (currentJibunAddr != null && currentJibunAddr != jibunAddr) {
                    jibunAddr = currentJibunAddr
                    onAddressSelected(fullRoadAddr, currentJibunAddr)
                }
            }
        }

        savedStateHandle?.getLiveData<String>("fullRoadAddr")?.observe(navController.currentBackStackEntry!!, observer)
        savedStateHandle?.getLiveData<String>("jibunAddr")?.observe(navController.currentBackStackEntry!!, observer)

        onDispose {
            savedStateHandle?.getLiveData<String>("fullRoadAddr")?.removeObserver(observer)
            savedStateHandle?.getLiveData<String>("jibunAddr")?.removeObserver(observer)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Title("내 농장 정보")
        DynamicStepProgressBars(colors = listOf(lightColors.green2, Color.Transparent))
        Address(
            fullRoadAddr = fullRoadAddr,
            jibunAddr = jibunAddr,
            onFullRoadAddrChange = { fullRoadAddr = it },
            onJubunAddrChange = { jibunAddr = it },
            onSearchClick = {
                navController.navigate("webview")
            }
        )
    }
}


@Composable
fun LocationSearchWebViewScreen(navController: NavController, addressSelectionListener: AddressSelectionListener) {
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
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    )
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

@Composable
private fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = ""
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        readOnly = true,
        textStyle = TextStyle(color = Color.Gray, fontSize = 16.sp),
        modifier = modifier
            .background(Color.Transparent)
            .padding(0.dp),
        decorationBox = { innerTextField ->
            Column {
                Box(
                    modifier = Modifier
                        .background(Color.Transparent, RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp) // Adjust padding as needed
                ) {
                    if (value.isEmpty()) {
                        Text(text = placeholder, style = TextStyle(color = Color.Gray, fontSize = 16.sp))
                    }
                    innerTextField()
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Color.Gray)
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    Column(modifier = Modifier.fillMaxWidth()) {
        DynamicStepProgressBars(colors = listOf(lightColors.green2)) // 한 개의 StepProgressBar
        Spacer(modifier = Modifier.height(16.dp))
        DynamicStepProgressBars(colors = listOf(lightColors.green2, Color.Transparent)) // 두 개의 StepProgressBar
        Spacer(modifier = Modifier.height(16.dp))
        DynamicStepProgressBars(colors = listOf(lightColors.green2, lightColors.green2)) // 두 개의 StepProgressBar
        Spacer(modifier = Modifier.height(16.dp))
        DynamicStepProgressBars(colors = listOf(lightColors.green2, lightColors.green2, lightColors.green3)) // 세 개의 StepProgressBar
    }
}

@Preview(showBackground = true)
@Composable
private fun NextButtonPreview(){
    NBDreamTheme {
        NextButton()
    }
}

@Composable
@Preview(showSystemUi = true)
private fun InputAddressPreview() {
    NBDreamTheme {
        Column(
            Modifier.padding(16.dp)
        ) {
            Title("내 농장 정보")
            DynamicStepProgressBars(colors = listOf(lightColors.green2, Color.Transparent))
            Address(
                fullRoadAddr = "",
                jibunAddr = "",
                onFullRoadAddrChange = {},
                onJubunAddrChange = {}
            ) {

            }
        }
    }
}