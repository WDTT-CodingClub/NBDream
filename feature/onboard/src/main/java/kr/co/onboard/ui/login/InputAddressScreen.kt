package kr.co.onboard.ui.login

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.ViewGroup
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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.KakaoMapSdk
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import kr.co.onboard.BuildConfig
import kr.co.ui.theme.ColorSet.Dream.lightColors
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.ui.widget.DreamCenterTopAppBar
import kr.co.ui.widget.NextButton
import timber.log.Timber

@Composable
internal fun InputAddressScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
    var fullRoadAddr by remember {
        mutableStateOf(savedStateHandle?.get<String>("fullRoadAddr") ?: "")
    }
    var jibunAddr by remember {
        mutableStateOf(savedStateHandle?.get<String>("jibunAddr") ?: "")
    }

    Scaffold(
        modifier = modifier.padding(16.dp),
        topBar = {
            DreamCenterTopAppBar(title = "내 농장 정보")
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            DynamicStepProgressBars(colors = listOf(MaterialTheme.colors.green2, Color.Transparent))
            StepText("1/2", modifier = Modifier)
            DescriptionText("내 농장의 주소지를 입력해주세요")
            Address(
                fullRoadAddr = fullRoadAddr,
                jibunAddr = jibunAddr,
                onFullRoadAddrChange = {
                    fullRoadAddr = it
                    savedStateHandle?.set("fullRoadAddr", it)
                },
                onJubunAddrChange = {
                    jibunAddr = it
                    savedStateHandle?.set("jibunAddr", it)
                },
                onSearchClick = {
                    navController.navigate("ADDRESS_FIND_ROUTE")
                }
            )
            KakaoMapScreen() //padding 없애야 함
            NextButton()
        }
    }
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
            .padding()
    ) {
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
    }
}

@Composable
fun KakaoMapScreen() {
    val context = LocalContext.current
    val apiKey = BuildConfig.KAKAO_API_KEY
    var mapView by remember { mutableStateOf<MapView?>(null) }
    var kakaoMap by remember { mutableStateOf<KakaoMap?>(null) }
    var isSdkInitialized by remember { mutableStateOf(false) }
    var isMapReady by remember { mutableStateOf(false) }

    //SDK를 초기화
    LaunchedEffect(apiKey) {
        try {
            KakaoMapSdk.init(context, apiKey) //SDK를 초기화
            isSdkInitialized = true
            Timber.tag("KakaoMap").d("SDK Initialized successfully")
        } catch (e: Exception) {
            Timber.tag("KakaoMap").d("Error initializing SDK: ${e.message}")
        }
    }

    //리소스 해제
    DisposableEffect(Unit) {
        onDispose {
            mapView?.let {
                it.pause() //지도 일시중지
                Timber.tag("KakaoMap").d("Map paused")
            }
        }
    }

    //화면에 지도 표시
    if (isSdkInitialized) { //SDK 초기화 확인
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            factory = { ctx ->
                MapView(ctx).apply {
                    mapView = this
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )

                    if (!isMapReady) {
                        isMapReady = true
                        //지도 라이프사이클과 준비 상태를 처리
                        start(MapLifeCycleCallbackImpl(), KakaoMapReadyCallbackImpl { map ->
                            kakaoMap = map
                            Timber.tag("KakaoMap").d("Map is ready")
                        })
                    }
                }
            },
            update = {
                mapView?.resume()
                Timber.tag("KakaoMap").d("Map resumed")
            }
        )
    } else {
        // 초기화가 완료되지 않았을 때 보여줄 로딩 UI 또는 메시지
        Text("Loading map...", modifier = Modifier.padding(16.dp))
    }
}

class MapLifeCycleCallbackImpl : MapLifeCycleCallback() {
    override fun onMapDestroy() {
        Timber.tag("KakaoMap").d("Map is destroyed")
        // 지도 API가 정상적으로 종료될 때 호출됨
    }

    override fun onMapError(error: Exception) {
        Timber.tag("KakaoMap").d("Map error: ${error.message}")
        // 인증 실패 및 지도 사용 중 에러가 발생할 때 호출됨
    }
}

class KakaoMapReadyCallbackImpl(val onMapReady: (KakaoMap) -> Unit) : KakaoMapReadyCallback() {
    override fun onMapReady(map: KakaoMap) {
        Timber.tag("KakaoMap").d("Map is ready")
        // 인증 후 API가 정상적으로 실행될 때 호출됨
//        onMapReady(map)
    }
}

//주소 찾기 버튼을 눌렀을 때 나오는 WebView
@Composable
fun LocationSearchWebViewScreen(
    addressSelectionListener: AddressSelectionListener
) {
    Scaffold(
        topBar = {

        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
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
                modifier = Modifier
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
private fun InputAddressScreenPreview() {
    NBDreamTheme {
        InputAddressScreen(
            modifier = Modifier,
            navController = NavController(LocalContext.current)
        )
    }
}
@Preview(showBackground = true)
@Composable
private fun DynamicStepProgressBarsPreview() {
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
private fun AddressPreview(){
    Address(fullRoadAddr = "허리도 가늘군 만지면 부서지리", jibunAddr = "jibunAddr", onFullRoadAddrChange = {}, onJubunAddrChange = {}) {

    }
}