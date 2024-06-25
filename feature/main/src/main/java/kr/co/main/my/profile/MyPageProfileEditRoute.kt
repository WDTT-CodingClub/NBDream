package kr.co.main.my.profile

import android.location.Geocoder
import android.net.Uri
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import kotlinx.coroutines.flow.collectLatest
import kr.co.common.util.FileUtil
import kr.co.main.R
import kr.co.ui.ext.noRippleClickable
import kr.co.ui.ext.scaffoldBackground
import kr.co.ui.icon.DreamIcon
import kr.co.ui.icon.dreamicon.Addpicture
import kr.co.ui.theme.NBDreamTheme
import kr.co.ui.theme.colors
import kr.co.ui.theme.typo
import kr.co.ui.widget.DreamCenterTopAppBar
import kr.co.ui.widget.DreamLocationSearchScreen
import timber.log.Timber
import java.util.Locale

@Composable
internal fun MyPageProfileEditRoute(
    popBackStack: () -> Unit,
    navigateToMyPage: () -> Unit,
    viewModel: MyPageProfileEditViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val geocoder = Geocoder(LocalContext.current, Locale.KOREA)

    val (addressVisible, setAddressVisible) = rememberSaveable {
        mutableStateOf(false)
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                FileUtil.getFileFromUri(it)?.let { file ->
                    viewModel.uploadImage(file)
                }
            }
        }
    )

    LaunchedEffect(Unit) {
        viewModel.complete.collectLatest {
            navigateToMyPage()
        }
    }

    MyPageProfileEditScreen(
        state = state,
        photoPickerLauncher = photoPickerLauncher,
        popBackStack = popBackStack,
        onClickAddress = { setAddressVisible(true) },
        onClickConfirm = viewModel::onClickConfirm,
        onNameChanged = viewModel::onNameChanged,
    )

    if (addressVisible) {
        DreamLocationSearchScreen { jibunAddress, bCode ->
            Timber.d("WebView jibunAddr: $jibunAddress, bCode: $bCode")
            viewModel.onAddressChanged(jibunAddress)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                geocoder.getFromLocationName(jibunAddress, 1) {
                    if (it.isNotEmpty()) {
                        viewModel.onCoordinateChanged(
                            latitude = it[0].latitude,
                            longitude = it[0].longitude
                        )
                    }
                }
            }
            setAddressVisible(false)
        }
    }
}

@Composable
private fun MyPageProfileEditScreen(
    photoPickerLauncher: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>,
    popBackStack: () -> Unit = {},
    onClickAddress: () -> Unit = {},
    onClickConfirm: () -> Unit = {},
    onNameChanged: (String) -> Unit = {},
    state: MyPageProfileEditViewModel.State = MyPageProfileEditViewModel.State(),
) {
    Scaffold(
        topBar = {
            DreamCenterTopAppBar(
                title = stringResource(R.string.feature_main_profile_edit),
                navigationIcon = {
                    IconButton(onClick = popBackStack) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                            contentDescription = stringResource(R.string.feature_main_pop_back_stack)
                        )
                    }
                },
                actions = {
                    TextButton(onClick = onClickConfirm) {
                        Text(
                            text = stringResource(R.string.feature_main_profile_edit_complete),
                            style = MaterialTheme.typo.body2,
                            color = MaterialTheme.colors.gray3
                        )
                    }
                }
            )
        }
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .scaffoldBackground(scaffoldPadding)
                .padding(top = 52.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .size(88.dp)
                    .noRippleClickable(onClick = {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    }),
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(88.dp)
                        .clip(CircleShape),
                    model = state.profileImageUrl,
                    contentScale = ContentScale.Crop,
                    contentDescription = stringResource(R.string.feature_main_profile_edit_image),
                )

                Icon(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(33.dp)
                        .semantics { },
                    imageVector = DreamIcon.Addpicture,
                    contentDescription = "",
                    tint = Color.Unspecified
                )
            }

            Spacer(modifier = Modifier.height(71.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.feature_main_profile_edit_nick_name),
                    style = MaterialTheme.typo.h4,
                    color = MaterialTheme.colors.gray1
                )

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.name ?: "",
                    onValueChange = onNameChanged,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                    ),
                )

            }
            state.nameGuide?.let {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = it,
                    style = MaterialTheme.typo.body2,
                    color = MaterialTheme.colors.error
                )
            }

            Spacer(modifier = Modifier.height(52.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.feature_main_profile_edit_my_adrress),
                    style = MaterialTheme.typo.h4,
                    color = MaterialTheme.colors.gray1
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        modifier = Modifier.fillMaxWidth(0.75f),
                        value = state.address ?: "농장을 등록해 주세요",
                        onValueChange = {},
                        enabled = false,
                        colors = TextFieldDefaults.colors(
                            disabledContainerColor = Color.Transparent,
                        ),
                    )
                    OutlinedButton(
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colors.white,
                            contentColor = MaterialTheme.colors.gray1
                        ),
                        border = BorderStroke(
                            width = 1.dp,
                            color = MaterialTheme.colors.gray1
                        ),
                        shape = RoundedCornerShape(12.dp),
                        onClick = onClickAddress,
                        contentPadding = PaddingValues(
                            vertical = 4.dp,
                            horizontal = 12.dp
                        )
                        ) {
                        Text(
                            text = "주소 찾기",
                            style = MaterialTheme.typo.label
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    NBDreamTheme {
        MyPageProfileEditScreen(
            popBackStack = {},
            onClickConfirm = {},
            onNameChanged = {},
            photoPickerLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.PickVisualMedia(),
                onResult = {}
            )
        )
    }
}