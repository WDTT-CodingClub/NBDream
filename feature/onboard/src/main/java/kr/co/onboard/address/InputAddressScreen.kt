package kr.co.onboard.address

import android.location.Geocoder
import android.os.Build
import android.view.ViewGroup
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.KakaoMapSdk
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.kakao.vectormap.camera.CameraUpdateFactory
import kotlinx.coroutines.launch
import kr.co.onboard.BuildConfig
import kr.co.onboard.R
import kr.co.ui.ext.scaffoldBackground
import kr.co.ui.theme.ColorSet.Dream.lightColors
import kr.co.ui.theme.Paddings
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.ui.widget.DreamCenterTopAppBar
import kr.co.ui.widget.DreamLocationSearchScreen
import kr.co.ui.widget.NextButton
import timber.log.Timber
import java.util.Locale

@Composable
internal fun InputAddressScreen(
    modifier: Modifier,
    viewModel: InputAddressViewModel = hiltViewModel(),
    popBackStack: () -> Unit = {},
    navController: NavController
) {
    val state by viewModel.state.collectAsState()

    val scope = rememberCoroutineScope()
    val geocoder = Geocoder(LocalContext.current, Locale.KOREA)

    val (locationSearchVisible, setLocationSearchVisible) = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        viewModel.showCropScreen.collect {
            navController.navigate(
                "SelectCropScreen/${state.fullRoadAddress}/${state.bCode}"
            )
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colors.white,
        topBar = {
            DreamCenterTopAppBar(title = stringResource(id = R.string.feature_onboard_my_farm_title),
                navigationIcon = {
                    IconButton(
                        onClick = popBackStack
                    ) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "arrowleft"
                        )
                    }
                }
            )
        }) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .scaffoldBackground(scaffoldPadding)
        ) {
            DynamicStepProgressBars(
                modifier, colors = listOf(MaterialTheme.colors.green4, MaterialTheme.colors.gray9)
            )

            Spacer(modifier = Modifier.height(52.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                DescriptionText(stringResource(id = R.string.feature_onboard_my_farm_address_description))
            }

            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Address(
                    modifier,
                    fullRoadAddr = state.fullRoadAddress,
                    onFullRoadAddrChange = {},
                    onSearchClick = {
                    setLocationSearchVisible(true)
                })

                KakaoMapScreen(
                    modifier = modifier,
                    latitude = state.latitude,
                    longitude = state.longitude
                )

                Spacer(modifier = modifier.weight(0.3f))

                NextButton(
                    skipId = R.string.feature_onboard_my_farm_skip_input,
                    nextId = R.string.feature_onboard_my_farm_next,
                    onNextClick = {
                        val fullRoadAddress = state.fullRoadAddress
                        val bCode = state.bCode
                        val latitude = state.latitude ?: 0.0
                        val longitude = state.longitude ?: 0.0

                        Timber.d("fullRoadAddress: $fullRoadAddress, bCode: $bCode, latitude: $latitude, longitude: $longitude")
                        navController.navigate(
                            "SelectCropScreen/$fullRoadAddress/$bCode/$latitude/$longitude"
                        )
                    },
                    onSkipClick = {
                        val fullRoadAddress = state.fullRoadAddress
                        val bCode = state.bCode
                        val latitude = state.latitude ?: 0.0
                        val longitude = state.longitude ?: 0.0

                        navController.navigate(
                            "SelectCropScreen/$fullRoadAddress/$bCode/$latitude/$longitude"
                        )
                    }
                )
            }
        }
    }

    if (locationSearchVisible) {
        DreamLocationSearchScreen { jibunAddress, bCode ->
            viewModel.updateAddresses(jibunAddress, bCode)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geocoder.getFromLocationName(jibunAddress, 1) {
                    if (it.isNotEmpty()) {
                        Timber.d("state latitude: ${it[0].latitude}, longitude: ${it[0].longitude}")
                        viewModel.onCoordinateChanged(
                            latitude = it[0].latitude, longitude = it[0].longitude
                        )
                    }
                }
            } else {
                scope.launch {
                    geocoder.getFromLocationName(jibunAddress, 1).let {
                        if (!it.isNullOrEmpty()) {
                            viewModel.onCoordinateChanged(
                                latitude = it[0].latitude,
                                longitude = it[0].longitude
                            )
                        }
                    }
                }
            }
            setLocationSearchVisible(false)
        }
    }
}

@Composable
private fun Address(
    modifier: Modifier,
    fullRoadAddr: String?,
    onFullRoadAddrChange: (String) -> Unit,
    onSearchClick: () -> Unit,
) {
    Column(
        modifier = modifier.padding()
    ) {
        Row(
            modifier = modifier.height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomTextField(
                value = fullRoadAddr ?: "",
                onValueChange = {
                    onFullRoadAddrChange(it)
                },
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .background(
                        color = MaterialTheme.colors.gray10,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(
                        horizontal = 16.dp,
                        vertical = 12.dp
                    ),
                placeholder = stringResource(id = R.string.feature_onboard_my_farm_address_placeholder)
            )
            TextButton(
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colors.white,
                    contentColor = MaterialTheme.colors.gray1
                ),
                shape = RoundedCornerShape(12.dp),
                onClick = onSearchClick,
                contentPadding = PaddingValues(
                    vertical = 4.dp,
                    horizontal = 12.dp
                )
            ) {
                Text(
                    stringResource(id = R.string.feature_onboard_my_farm_address_find),
                    style = MaterialTheme.typo.body1,
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}

@Composable
private fun StepProgressBar(
    modifier: Modifier,
    color: Color,
) {
    Box(
        modifier = modifier
            .height(5.dp)
            .background(
                color = color
            )
            .then(
                if (color == Color.Transparent) modifier.border(
                    0.5.dp, Color.Gray, RoundedCornerShape(4.dp)
                )
                else modifier
            )

    )
}

@Composable
fun DynamicStepProgressBars(
    modifier: Modifier,
    colors: List<Color>,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        colors.forEach { color ->
            StepProgressBar(
                color = color, modifier = modifier.weight(1f)
            )
        }
    }
}

@Composable
fun DescriptionText(
    text: String,
) {
    Text(
        text,
        style = MaterialTheme.typo.header2M,
    )
}

@Composable
fun KakaoMapScreen(
    modifier: Modifier,
    latitude: Double?,
    longitude: Double?,
) {
    val context = LocalContext.current
    val apiKey = BuildConfig.KAKAO_NATIVE_APP_KEY
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
        Spacer(modifier = Modifier.height(52.dp))
        AndroidView(
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            factory = { ctx ->
                MapView(ctx).apply {
                    mapView = this
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
                    )

                    if (!isMapReady) {
                        isMapReady = true
                        //지도 라이프사이클과 준비 상태를 처리
                        start(
                            MapLifeCycleCallbackImpl(),
                            object : KakaoMapReadyCallback() {
                                override fun onMapReady(map: KakaoMap) {
                                    kakaoMap = map
                                }
                            }
                        )
                    }
                }
            }, update = {
                mapView?.resume()
                kakaoMap?.also {
                    latitude?.let { latitude ->
                        LatLng.from(latitude, longitude!!).let { position ->
                            it.moveCamera(
                                CameraUpdateFactory.newCenterPosition(
                                    position,
                                    17
                                )
                            )
                        }
                    }
                }
            })
    } else {
        Text("Loading map...", modifier = modifier.padding(Paddings.xlarge))
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

@Composable
private fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
) {
    BasicTextField(value = value,
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
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = TextStyle(color = Color.Gray, fontSize = 16.sp)
                        )
                    }
                    innerTextField()
                }
            }
        })
}

//@Preview(showBackground = true)
//@Composable
//private fun InputAddressScreenPreview() {
//    NBDreamTheme {
//        InputAddressScreen(
//            modifier = Modifier,
//        )
//    }
//}

@Preview(showBackground = true)
@Composable
private fun DynamicStepProgressBarsPreview() {
    Column(modifier = Modifier.fillMaxWidth()) {
        DynamicStepProgressBars(
            Modifier, colors = listOf(lightColors.green2)
        )
        Spacer(modifier = Modifier.height(16.dp))
        DynamicStepProgressBars(
            Modifier, colors = listOf(lightColors.green2, Color.Transparent)
        )
        Spacer(modifier = Modifier.height(16.dp))
        DynamicStepProgressBars(
            Modifier, colors = listOf(lightColors.green2, lightColors.green2)
        )
        Spacer(modifier = Modifier.height(16.dp))
        DynamicStepProgressBars(
            Modifier, colors = listOf(lightColors.green2, lightColors.green2, lightColors.green3)
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun AddressPreview() {
//    Address(
//        Modifier,
//        fullRoadAddr = "허리도 가늘군 만지면 부서지리",
//        jibunAddr = "jibunAddr",
//        onFullRoadAddrChange = {},
//        onJibunAddrChange = {},
//        onSearchClick = {}
//    )
//}